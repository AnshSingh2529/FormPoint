package coading.champ.online_form_india.helper

import android.graphics.Bitmap
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class Validation {

    companion object {

        fun validateEmail(email: String, error: TextView): Boolean {
            error.visibility = View.VISIBLE
            return if (email.isBlank()) {
                error.visibility = View.GONE
                true
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error.text = "Enter valid email address"
                false
            } else {
                error.visibility = View.GONE
                true
            }
        }

        fun validateMandatoryEmail(email: String, error: TextView): Boolean {
            error.visibility = View.VISIBLE
            return if (email.isBlank()) {
                error.text = "Email Cannot Be Empty"
                false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error.text = "Enter valid email address"
                false
            } else {
                error.visibility = View.GONE
                true
            }
        }

        fun validateMobile(mobileNo: String, error: TextView): Boolean {
            error.visibility = View.VISIBLE
            return if (mobileNo.isEmpty()) {
                error.text = "Mobile No. is cannot be empty"
                false
            } else if (!mobileNo.matches("^[6789]\\d{9}$".toRegex())) {
                error.text = "Enter valid Mobile no."
                false
            } else {
                error.visibility = View.GONE
                true
            }
        }

        fun validateTheImage(layout: LinearLayout, bitmap: Bitmap?, textViewError: TextView) :Boolean {

            textViewError.visibility = View.VISIBLE
            return if (layout.visibility == View.VISIBLE){
                if (bitmap != null){
                    textViewError.visibility = View.GONE
                    true
                }else{
                    textViewError.text = "Please Upload File On This Field"
                    false
                }

            }else{
                textViewError.visibility = View.GONE
                true
            }
        }

        fun validateName(name: String, personNameError: TextView): Boolean {
            personNameError.visibility = View.VISIBLE
            if (name.isEmpty()) {
                personNameError.text = "Name is cannot be empty."
                return false
            }
            personNameError.visibility = View.GONE
            return true
        }

        fun validateAddress(name: String, personNameError: TextView): Boolean {
            personNameError.visibility = View.VISIBLE
            if (name.isEmpty()) {
                personNameError.text = "Address is cannot be empty."
                return false
            }
            personNameError.visibility = View.GONE
            return true
        }


        fun validateCnfPass(
            pass: String,
            cnf: String,
            confirmPasswordError: TextView,
            passError: TextView
        ): Boolean {
            confirmPasswordError.visibility = View.VISIBLE
            return if (pass.isEmpty()) {
                confirmPasswordError.text = "Confirm Password is cannot be empty"
                false
            } else if (pass != cnf) {
                passError.visibility = View.VISIBLE
                confirmPasswordError.text = "Password and confirm password must be same"
                passError.text = "Password and confirm password must be same"
                false
            } else {
                confirmPasswordError.visibility = View.GONE
                true
            }
        }

        fun validatePass(pass: String, passwordError: TextView): Boolean {
            passwordError.visibility = View.VISIBLE
            return if (pass.isEmpty()) {
                passwordError.text = "Password is cannot be empty"
                false
            } else if (pass.length < 4) {
                passwordError.text = "Password too short, minimum 4 character required"
                false
            }/* else if (!pass.matches("^(?=.*[A-Za-z])(?=.*\\d)(?!.*\\s).{6,}$".toRegex())) {
                passwordError.text = "Password Include Capital and small and one number"
                false
            }*/ else {
                passwordError.visibility = View.GONE
                true
            }
        }

        fun validateTransaction(string: String, recieptError: TextView): Boolean {
            recieptError.visibility = View.VISIBLE
            return if (string.isEmpty() or string.equals("null")){
                recieptError.text = "This field is required"
                false
            }else {
                recieptError.text = ""
                recieptError.visibility = View.GONE
                true
            }
        }

    }


}