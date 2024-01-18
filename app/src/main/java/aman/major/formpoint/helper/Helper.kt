package aman.major.formpoint.helper

import aman.major.formpoint.R
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView

class Helper {


    companion object{
        var passwordVisible = true

        fun manageEyeIcon(password: ImageView, passEditText: EditText) {
            if (passwordVisible) {
                password.setImageResource(R.drawable.ic_closed_eye)
                passEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
            } else {
                password.setImageResource(R.drawable.ic_open_eye)
                passEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordVisible = true
            }
        }

    }

}