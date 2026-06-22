package com.app.sehatcare.chat.presentation.util



import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.app.sehatcare.R

object MessageFormatter {

    /**
     * Parses the bot response and returns a SpannableStringBuilder
     * with proper formatting:
     *  - **text**     → bold
     *  - # text       → H1 (large bold)
     *  - ## text      → H2 (medium bold)
     *  - • item       → bullet point
     *  - 1. item      → numbered item (kept as-is, styled bold number)
     */
    fun format(context: Context, raw: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        val lines = raw.split("\n")

        lines.forEachIndexed { index, line ->
            when {
                // H1: # Title
                line.startsWith("# ") -> {
                    val text = line.removePrefix("# ").trim()
                    val start = ssb.length
                    ssb.append(text)
                    ssb.setSpan(StyleSpan(Typeface.BOLD), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    ssb.setSpan(AbsoluteSizeSpan(18, true), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                // H2: ## Title
                line.startsWith("## ") -> {
                    val text = line.removePrefix("## ").trim()
                    val start = ssb.length
                    ssb.append(text)
                    ssb.setSpan(StyleSpan(Typeface.BOLD), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    ssb.setSpan(AbsoluteSizeSpan(16, true), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                // Bullet: • item or - item or * item
                line.startsWith("• ") || line.startsWith("- ") || line.startsWith("* ") -> {
                    val text = line.drop(2).trim()
                    val start = ssb.length
                    ssb.append(applyInlineBold(text))
                    ssb.setSpan(
                        BulletSpan(16, ContextCompat.getColor(context, R.color.color_primary)),
                        start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                // Numbered list: 1. item, 2. item etc.
                line.matches(Regex("^\\d+\\.\\s.*")) -> {
                    val dotIndex = line.indexOf(". ")
                    val number = line.substring(0, dotIndex + 1)  // "1."
                    val text   = line.substring(dotIndex + 2)      // "item text"
                    val start  = ssb.length
                    ssb.append(number)
                    ssb.setSpan(StyleSpan(Typeface.BOLD), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    ssb.append(" ")
                    ssb.append(applyInlineBold(text))
                }

                // Empty line → paragraph break
                line.isBlank() -> ssb.append("\n")

                // Normal line — may contain **bold**
                else -> ssb.append(applyInlineBold(line))
            }

            // Add newline between lines (except trailing)
            if (index < lines.size - 1 && !line.isBlank()) ssb.append("\n")
        }

        return ssb
    }

    /**
     * Processes **bold** markers within a single line.
     */
    private fun applyInlineBold(text: String): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        val regex = Regex("\\*\\*(.+?)\\*\\*")
        var lastEnd = 0

        regex.findAll(text).forEach { match ->
            // Append text before bold
            ssb.append(text.substring(lastEnd, match.range.first))
            // Append bold text
            val start = ssb.length
            ssb.append(match.groupValues[1])
            ssb.setSpan(StyleSpan(Typeface.BOLD), start, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            lastEnd = match.range.last + 1
        }
        // Append remaining text
        if (lastEnd < text.length) ssb.append(text.substring(lastEnd))
        return ssb
    }
}