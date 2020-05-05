package pw.cub3d.lemmy.core.utility

import android.content.Context
import org.json.JSONObject
import pw.cub3d.lemmy.R
import java.lang.Exception
import java.util.regex.Pattern

object EmojiFormat {

    fun recur(json: JSONObject, name: String): String? {
        println("Searching for $name")
        var res: String? = null
        for (it in json.keys()) {
            println("Checking $it")
            try {
                val child = json.getJSONObject(it)
                res = recur(child, name)
            } catch (e: Exception) { } finally {
                val v = json.getString(it)
                if(v == name) {
                    res = it
                }
            }
            if (res != null)
                break
        }
        return res
    }

    fun formatText(ctx: Context, text: String): String {
        val emoji = JSONObject(ctx.resources.openRawResource(R.raw.emoji).bufferedReader().readText())

        val re = Pattern.compile(":[\\w\\s]+:").toRegex()
        val matches = re.findAll(text)
        var newText = text
        matches.forEach {
            recur(emoji, it.value.substring(1, it.value.length - 1))?.let { unicode ->
                newText = newText.replace(it.value, unicode)
            }
        }

        return newText
    }
}