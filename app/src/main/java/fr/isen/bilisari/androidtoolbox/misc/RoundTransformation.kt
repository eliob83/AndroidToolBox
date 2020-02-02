package fr.isen.bilisari.androidtoolbox.misc

import android.graphics.*
import com.squareup.picasso.Transformation


class RoundTransformation internal constructor(private val radius: Float, private val margin: Float) : Transformation {
    override fun transform(source: Bitmap) : Bitmap {
        val paint = Paint()

        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // Output will have the same dimensions as the source
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        // Dimensions of the circle
        canvas.drawRoundRect(RectF(margin, margin, (source.width - margin), (source.height - margin)), radius, radius, paint)

        // Free the source if not needed
        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key() : String {
        return "round(radius=$radius, margin=$margin"
    }
}