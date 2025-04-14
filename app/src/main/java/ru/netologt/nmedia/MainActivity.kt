package ru.netologt.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netologt.nmedia.adapter.PostAdapter
import ru.netologt.nmedia.adapter.PostEventListener
import ru.netologt.nmedia.databinding.ActivityMainBinding
import ru.netologt.nmedia.dto.Post
import ru.netologt.nmedia.util.AndroidUtils
import ru.netologt.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private var isEditing: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        binding.group.visibility = View.GONE

        val adapter = PostAdapter(
            object : PostEventListener {
                override fun onEdit(post: Post) {
                    if (!isEditing) {
                        isEditing = true
                        viewModel.editContent(post)
                        binding.group.visibility = View.VISIBLE
                    }
                }

                override fun onRemove(post: Post) {
                    if (!isEditing) {
                        viewModel.removeById(post.id)
                    }
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }
            }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) binding.list.smoothScrollToPosition(0)
            }
        }

        viewModel.edited.observe(this) {
            with(binding.message) {
                text = it.content
            }
            binding.content.setText(it.content)

        }

        binding.add.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.saveContent()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyBoard(this)
                binding.group.visibility = View.GONE
                isEditing = false
            }
        }

        binding.cancel.setOnClickListener {
            with(binding.content) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyBoard(this)
                binding.group.visibility = View.GONE
                viewModel.saveContent()
                isEditing = false
            }
        }
    }
}
