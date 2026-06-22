package com.app.sehatcare.chat.presentation.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.sehatcare.chat.domain.model.ChatMessage
import com.app.sehatcare.chat.domain.model.MessageSender
import com.app.sehatcare.chat.presentation.util.MessageFormatter
import com.app.sehatcare.databinding.ChatItemBotBinding
import com.app.sehatcare.databinding.ChatItemTypingBinding
import com.app.sehatcare.databinding.ChatItemUserBinding

private const val VIEW_TYPE_USER   = 0
private const val VIEW_TYPE_BOT    = 1
private const val VIEW_TYPE_TYPING = 2

class ChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(old: ChatMessage, new: ChatMessage) = old.id == new.id
        // During streaming the text changes, so contents differ — forces rebind
        override fun areContentsTheSame(old: ChatMessage, new: ChatMessage) = old == new
    }).build()
) {

    override fun getItemViewType(position: Int): Int {
        val msg = getItem(position)
        return when {
            msg.isTyping               -> VIEW_TYPE_TYPING
            msg.sender == MessageSender.USER -> VIEW_TYPE_USER
            else                       -> VIEW_TYPE_BOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_USER   -> UserViewHolder(ChatItemUserBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TYPING -> TypingViewHolder(ChatItemTypingBinding.inflate(inflater, parent, false))
            else             -> BotViewHolder(ChatItemBotBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = getItem(position)
        when (holder) {
            is UserViewHolder   -> holder.bind(msg)
            is BotViewHolder    -> holder.bind(msg)
            is TypingViewHolder -> holder.startAnimation()
        }
    }

    // ── ViewHolders ────────────────────────────────────────────

    class UserViewHolder(private val b: ChatItemUserBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(msg: ChatMessage) {
            b.tvMessage.text = msg.text
        }
    }

    class BotViewHolder(private val b: ChatItemBotBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(msg: ChatMessage) {
            b.tvMessage.text = MessageFormatter.format(b.root.context, msg.text)

            // Error styling
            b.root.alpha = if (msg.isError) 0.75f else 1f
            if (msg.isError) {
                b.tvMessage.setTextColor(
                    b.root.context.getColor(com.app.sehatcare.R.color.color_error)
                )
            } else {
                b.tvMessage.setTextColor(
                    b.root.context.getColor(com.app.sehatcare.R.color.color_on_surface_variant)
                )
            }
        }
    }

    class TypingViewHolder(private val b: ChatItemTypingBinding) : RecyclerView.ViewHolder(b.root) {
        fun startAnimation() {
            // The three dots are animated via AnimatedVectorDrawable in the XML
            // Nothing to set here — animation plays automatically via XML state
        }
    }
}