package com.app.sehatcare.chat.presentation.ui



import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.sehatcare.base.BaseFragment
import com.app.sehatcare.chat.presentation.adapter.ChatAdapter
import com.app.sehatcare.chat.presentation.viewmodel.ChatViewModel
import com.app.sehatcare.common.extensions.collectLifecycleFlow
import com.app.sehatcare.databinding.ChatFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun setupViews() {
        chatAdapter = ChatAdapter()

        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true   // new messages push up from bottom
            }
            adapter = chatAdapter
            itemAnimator = null       // smoother streaming — no default flash animation
        }

        // Send button / IME action
        binding.btnSend.setOnClickListener { dispatchMessage() }
        binding.etMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) { dispatchMessage(); true } else false
        }

        // Enable send button only when input has text
        binding.etMessage.doOnTextChanged { text, _, _, _ ->
            binding.btnSend.isEnabled = !text.isNullOrBlank()
        }

    }

    override fun observeData() {
        viewModel.messages.collectLifecycleFlow(this) { messages ->
            chatAdapter.submitList(messages) {
                // Auto-scroll to newest message after list update
                binding.rvMessages.smoothScrollToPosition(messages.size - 1)
            }
        }

        viewModel.isSending.collectLifecycleFlow(this) { sending ->
            binding.btnSend.isEnabled = !sending && binding.etMessage.text?.isNotBlank() == true
            binding.etMessage.isEnabled = !sending
        }
    }

    private fun dispatchMessage() {
        val text = binding.etMessage.text?.toString() ?: return
        if (text.isBlank()) return
        viewModel.sendMessage(text)
        binding.etMessage.setText("")
    }
}