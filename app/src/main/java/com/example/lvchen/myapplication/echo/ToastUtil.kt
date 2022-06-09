package tech.echoing.base.util

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import tech.echoing.base.R
import tech.echoing.base.base.BaseApplication
import tech.echoing.libs.utils.SizeUtils

/**
 * Created by Crazy on 19-8-30
 */
object ToastUtil {

    @ColorInt
    private var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")

    @ColorInt
    private var ERROR_COLOR = Color.parseColor("#D50000")

    @ColorInt
    private var INFO_COLOR = Color.parseColor("#3F51B5")

    @ColorInt
    private var SUCCESS_COLOR = Color.parseColor("#388E3C")

    @ColorInt
    private var WARNING_COLOR = Color.parseColor("#FFA900")

    @ColorInt
    private val NORMAL_COLOR = Color.parseColor("#353A3E")

    private val LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    private var currentTypeface =
        LOADED_TOAST_TYPEFACE
    private var textSize = 16 // in SP

    private var tintIcon = true


    private lateinit var context: Application
    fun init(app: Application, config: Config = Config.get()) {
        context = app
        config.apply()
    }

    private val mainLooper = Looper.getMainLooper()
    private val toastMap by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        mapOf(
            style.NORMAL to normal(
                "",
                Toast.LENGTH_LONG
            ),
            style.SUCCESS to success(
                "",
                Toast.LENGTH_LONG
            ),
            style.ERROR to error(
                "",
                Toast.LENGTH_LONG
            ),
            style.INFO to info(
                "",
                Toast.LENGTH_LONG
            ),
            style.WARN to warning(
                "",
                Toast.LENGTH_LONG
            )
        )
    }

    @JvmStatic
    @JvmOverloads
    fun toast(msg: String, style: style = ToastUtil.style.NORMAL) {
        if (mainLooper == Looper.myLooper()) {
            toastMap.getValue(style)
                .apply {
                    setText(msg)
                    show()
                }
        } else {
            Looper.prepare()
            normal(msg, Toast.LENGTH_SHORT)
                .show()
            Looper.loop()
        }
    }

    @JvmStatic
    @JvmOverloads
    fun toast(@StringRes strId: Int, style: style = ToastUtil.style.NORMAL) =
        toast(
            context.getString(strId), style
        )

    enum class style {
        NORMAL, SUCCESS, ERROR, INFO, WARN
    }


    @CheckResult
    fun normal(@StringRes message: Int): Toast {
        return normal(
            context.getString(
                message
            ), Toast.LENGTH_SHORT, null, false
        )
    }

    @CheckResult
    fun normal(message: CharSequence): Toast {
        return normal(
            message,
            Toast.LENGTH_SHORT,
            null,
            false
        )
    }

    @CheckResult
    fun normal(@StringRes message: Int, icon: Drawable): Toast {
        return normal(
            context.getString(
                message
            ), Toast.LENGTH_SHORT, icon, true
        )
    }

    @CheckResult
    fun normal(message: CharSequence, icon: Drawable): Toast {
        return normal(
            message,
            Toast.LENGTH_SHORT,
            icon,
            true
        )
    }

    @CheckResult
    fun normal(@StringRes message: Int, duration: Int): Toast {
        return normal(
            context.getString(
                message
            ), duration, null, false
        )
    }

    @CheckResult
    fun normal(message: CharSequence, duration: Int): Toast {
        return normal(
            message,
            duration,
            null,
            false
        )
    }

    @CheckResult
    fun normal(
        @StringRes message: Int, duration: Int,
        icon: Drawable
    ): Toast {
        return normal(
            context.getString(
                message
            ), duration, icon, true
        )
    }

    @CheckResult
    fun normal(
        @StringRes message: Int, duration: Int,
        icon: Drawable, withIcon: Boolean
    ): Toast {
        return custom(
            context.getString(
                message
            ),
            icon,
            NORMAL_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    @JvmOverloads
    fun normal(
        message: CharSequence, duration: Int,
        icon: Drawable?, withIcon: Boolean = true
    ): Toast {
        return custom(
            message,
            icon,
            NORMAL_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun warning(@StringRes message: Int): Toast {
        return warning(
            context.getString(
                message
            ), Toast.LENGTH_SHORT, true
        )
    }

    @CheckResult
    fun warning(@StringRes message: Int, duration: Int): Toast {
        return warning(
            context.getString(
                message
            ), duration, true
        )
    }

    @CheckResult
    fun warning(@StringRes message: Int, duration: Int, withIcon: Boolean): Toast {
        return custom(
            context.getString(message),
            ToastyUtils.getDrawable(R.drawable.ic_error_outline_white_48dp),
            WARNING_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    @JvmOverloads
    fun warning(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true
    ): Toast {
        return custom(
            message,
            ToastyUtils.getDrawable(R.drawable.ic_error_outline_white_48dp),
            WARNING_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun info(@StringRes message: Int): Toast {
        return info(
            context.getString(
                message
            ), Toast.LENGTH_SHORT, true
        )
    }

    @CheckResult
    fun info(@StringRes message: Int, duration: Int): Toast {
        return info(
            context.getString(
                message
            ), duration, true
        )
    }

    @CheckResult
    fun info(@StringRes message: Int, duration: Int, withIcon: Boolean): Toast {
        return custom(
            context.getString(message),
            ToastyUtils.getDrawable(R.drawable.ic_info_outline_white_48dp),
            INFO_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    @JvmOverloads
    fun info(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true
    ): Toast {
        return custom(
            message,
            ToastyUtils.getDrawable(R.drawable.ic_info_outline_white_48dp),
            INFO_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun success(@StringRes message: Int): Toast {
        return success(
            context.getString(
                message
            ), Toast.LENGTH_SHORT, true
        )
    }

    @CheckResult
    fun success(@StringRes message: Int, duration: Int): Toast {
        return success(
            context.getString(
                message
            ), duration, true
        )
    }

    @CheckResult
    fun success(@StringRes message: Int, duration: Int, withIcon: Boolean): Toast {
        return custom(
            context.getString(message),
            ToastyUtils.getDrawable(R.drawable.ic_check_white_48dp),
            SUCCESS_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    @JvmOverloads
    fun success(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true
    ): Toast {
        return custom(
            message,
            ToastyUtils.getDrawable(R.drawable.ic_check_white_48dp),
            SUCCESS_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun error(@StringRes message: Int): Toast {
        return error(
            context.getString(
                message
            ), Toast.LENGTH_SHORT, true
        )
    }

    @CheckResult
    fun error(@StringRes message: Int, duration: Int): Toast {
        return error(
            context.getString(
                message
            ), duration, true
        )
    }

    @CheckResult
    fun error(@StringRes message: Int, duration: Int, withIcon: Boolean): Toast {
        return custom(
            context.getString(message),
            ToastyUtils.getDrawable(R.drawable.ic_clear_white_48dp),
            ERROR_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    @JvmOverloads
    fun error(
        message: CharSequence,
        duration: Int = Toast.LENGTH_SHORT,
        withIcon: Boolean = true
    ): Toast {
        return custom(
            message,
            ToastyUtils.getDrawable(R.drawable.ic_clear_white_48dp),
            ERROR_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun custom(
        @StringRes message: Int, icon: Drawable,
        duration: Int, withIcon: Boolean
    ): Toast {
        return custom(
            context.getString(
                message
            ), icon, -1, duration, withIcon, false
        )
    }

    @CheckResult
    fun custom(
        message: CharSequence, icon: Drawable,
        duration: Int, withIcon: Boolean
    ): Toast {
        return custom(
            message,
            icon,
            -1,
            duration,
            withIcon,
            false
        )
    }

    @CheckResult
    fun custom(
        @StringRes message: Int, @DrawableRes iconRes: Int,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast {
        return custom(
            context.getString(message),
            ToastyUtils.getDrawable(iconRes),
            tintColor,
            duration,
            withIcon,
            shouldTint
        )
    }

    @CheckResult
    fun custom(
        message: CharSequence, @DrawableRes iconRes: Int,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast {
        return custom(
            message,
            ToastyUtils.getDrawable(iconRes),
            tintColor,
            duration,
            withIcon,
            shouldTint
        )
    }

    @CheckResult
    fun custom(
        @StringRes message: Int, icon: Drawable,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast {
        return custom(
            context.getString(message),
            icon,
            tintColor,
            duration,
            withIcon,
            shouldTint
        )
    }

    @SuppressLint("ShowToast")
    @CheckResult
    fun custom(
        message: CharSequence, icon: Drawable?,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast {
        var icon = icon
        val currentToast = Toast.makeText(context, "", duration)
        val toastLayout = View.inflate(context, R.layout.toast_layout, null)
        val toastIcon = toastLayout.findViewById<ImageView>(R.id.toast_icon)
        val toastTextView = toastLayout.findViewById<TextView>(android.R.id.message)
        val drawableFrame: Drawable?

        if (shouldTint)
            drawableFrame =
                ToastyUtils.tint9PatchDrawableFrame(
                    tintColor
                )
        else
            drawableFrame =
                ToastyUtils.getDrawable(R.drawable.toast_frame)
        ToastyUtils.setBackground(
            toastLayout,
            drawableFrame
        )

        if (withIcon) {
            if (icon == null)
                throw IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true")
            if (tintIcon)
                icon =
                    ToastyUtils.tintIcon(
                        icon,
                        DEFAULT_TEXT_COLOR
                    )
            ToastyUtils.setBackground(
                toastIcon,
                icon
            )
        } else {
            toastIcon.visibility = View.GONE
        }

        toastTextView.text = message
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR)
//        toastTextView.typeface = currentTypeface
        toastTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            textSize.toFloat()
        )

        val container = currentToast.view as ViewGroup
        container.setBackgroundColor(Color.TRANSPARENT)
        container.removeAllViews()
        container.addView(toastLayout)
        currentToast.setGravity(
            Gravity.BOTTOM,
            0,
            SizeUtils.sp2px(20f)
        )
        return currentToast
    }

    class Config private constructor()// avoiding instantiation
    {
        @ColorInt
        var textColor = DEFAULT_TEXT_COLOR

        @ColorInt
        var errorColor = ERROR_COLOR

        @ColorInt
        var infoColor = INFO_COLOR

        @ColorInt
        var successColor = SUCCESS_COLOR

        @ColorInt
        var warningColor = WARNING_COLOR

        var typeface = currentTypeface
        var textSize = ToastUtil.textSize

        var tintIcon = ToastUtil.tintIcon

        fun apply() {
            DEFAULT_TEXT_COLOR =
                DEFAULT_TEXT_COLOR
            ERROR_COLOR =
                ERROR_COLOR
            INFO_COLOR =
                INFO_COLOR
            SUCCESS_COLOR =
                SUCCESS_COLOR
            WARNING_COLOR =
                WARNING_COLOR
            currentTypeface = typeface
            ToastUtil.textSize = textSize
            ToastUtil.tintIcon = tintIcon
        }

        companion object {

            @CheckResult
            fun get(): Config {
                return Config()
            }

            fun reset() {
                DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
                ERROR_COLOR = Color.parseColor("#D50000")
                INFO_COLOR = Color.parseColor("#3F51B5")
                SUCCESS_COLOR = Color.parseColor("#388E3C")
                WARNING_COLOR = Color.parseColor("#FFA900")
                currentTypeface =
                    LOADED_TOAST_TYPEFACE
                textSize = 16
                tintIcon = true
            }
        }
    }

    private object ToastyUtils {

        internal fun tintIcon(drawable: Drawable, @ColorInt tintColor: Int): Drawable {
            drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
            return drawable
        }

        internal fun tint9PatchDrawableFrame(@ColorInt tintColor: Int): Drawable {
            val toastDrawable =
                getDrawable(R.drawable.toast_frame)
            return tintIcon(
                toastDrawable,
                tintColor
            )
        }

        internal fun setBackground(view: View, drawable: Drawable?) {
            view.background = drawable
        }

        internal fun getDrawable(@DrawableRes id: Int): Drawable {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                context.getDrawable(id)!!
            else
                context.resources.getDrawable(id)
        }
    }

}

fun String.normalToast() = ToastUtil.toast(
    this,
    ToastUtil.style.NORMAL
)

fun String.successToast() = ToastUtil.toast(
    this,
    ToastUtil.style.SUCCESS
)

fun String.errorToast() = ToastUtil.toast(
    this,
    ToastUtil.style.ERROR
)

fun String.warnToast() = ToastUtil.toast(
    this,
    ToastUtil.style.WARN
)

fun String.infoToast() = ToastUtil.toast(
    this,
    ToastUtil.style.INFO
)