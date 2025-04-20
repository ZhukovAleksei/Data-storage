package ru.netologt.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netologt.nmedia.R
import ru.netologt.nmedia.adapter.PostAdapter
import ru.netologt.nmedia.adapter.PostEventListener
import ru.netologt.nmedia.databinding.ActivityMainBinding
import ru.netologt.nmedia.dto.Post
import ru.netologt.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostResultContract()) { text ->   //NewPostActivityContract
            text?.let {
                viewModel.changeContent(it)
                viewModel.saveContent()
            }
        }

        val editPostActivityContract =
            registerForActivityResult(IntentHandlerActivity()) { text ->
                text?.let {
                    viewModel.changeContent(it)
                    viewModel.saveContent()

                }
                viewModel.clearEdited()
            }

        val adapter = PostAdapter(
            object : PostEventListener {

                override fun onEdit(post: Post) {
                    viewModel.editContent(post)
                    editPostActivityContract.launch(post.content)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val sharedIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(sharedIntent)
                }

                override fun onVideo(post: Post) {
                    val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    startActivity(intentVideo)
                }
            }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(this)
        { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) binding.list.smoothScrollToPosition(0)
            }
        }

        binding.fab.setOnClickListener {
            newPostContract.launch()
        }
    }
}