package com.example.tbc_course_7

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.tbc_course_7.databinding.ActivityMainBinding
import com.example.tbc_course_7.models.User
import com.google.android.material.textfield.TextInputLayout
import java.nio.file.Files.exists

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val loggedUsers = mutableListOf<User>()
    private var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addBtn.setOnClickListener {
            addUser(userInformationChecker())
        }
        binding.removeBtn.setOnClickListener {
            removeUser(userInformationChecker())
        }
        binding.updateBtn.setOnClickListener {
            updateUser(userInformationChecker())
        }


    }

    private fun updateUser(user: User) {
        loggedUsers.forEach {
            if (it.email == user.email) {
                loggedUsers[loggedUsers.indexOf(it)] = user
                binding.successTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.GREEN)
                    binding.emailUserTextView.isInvisible = true
                    text = buildString {
                        append(getString(R.string.update_success))
                    }
                }

            }
            else {
                binding.successTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.RED)
                    text = buildString {
                        append(binding.firstNameEditText.text)
                        append(" ")
                        append(binding.lastNameEditText.text)
                        append(getString(R.string.not_found))
                    }
                }
            }
        }
    }

    private fun removeUser(user: User) {
        if (!isAllFilled() && isEmailValid()) {
            if (!loggedUsers.contains(user)) {
                binding.emailUserTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.RED)
                    text = binding.emailEditText.text
                }
                binding.successTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.RED)
                    text = buildString {
                        append(binding.firstNameEditText.text)
                        append(" ")
                        append(binding.lastNameEditText.text)
                        append(getString(R.string.is_not_exists))
                    }
                }
            } else {
                loggedUsers.remove(user)
                userRemoveCount()
                binding.emailUserTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.GREEN)
                    text = binding.emailEditText.text
                }
                binding.successTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.GREEN)
                    text = buildString {
                        append(binding.firstNameEditText.text)
                        append(" ")
                        append(binding.lastNameEditText.text)
                        append(getString(R.string.deleted))
                    }
                }
            }
            setError(binding.mainTheme, null)
        } else {
            setError(binding.mainTheme, getString(R.string.value))
        }

    }

    private fun addUser(user: User) {
        if (!isAllFilled() && isEmailValid()) {
            if (!loggedUsers.contains(user)) {
                loggedUsers.add(user)
                userAddCount()
                binding.emailUserTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.GREEN)
                    text = binding.emailEditText.text
                }
                binding.successTextView.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.GREEN)
                    text = buildString {
                        append(binding.firstNameEditText.text)
                        append(" ")
                        append(binding.lastNameEditText.text)
                        append(getString(R.string.successfully_added))
                    }
                }
            }

            setError(binding.mainTheme, null)


        } else {
            setError(binding.mainTheme, getString(R.string.value))
        }

    }

    private fun userInformationChecker(): User {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val age = binding.ageEditText.text.toString()
        return User(firstName, lastName, email, age)
    }

    private fun userAddCount() {
        binding.addedUserTextView.text = loggedUsers.size.toString()
    }

    private fun userRemoveCount() {

        binding.removeUserTextView.text = counter++.toString()
        binding.addedUserTextView.text = loggedUsers.size.toString()
    }

    private fun isAllFilled(): Boolean = with(binding) {
        return@with binding.emailEditText.text.toString().isEmpty() ||
                binding.firstNameEditText.text.toString().isEmpty() ||
                binding.lastNameEditText.text.toString().isEmpty() ||
                binding.ageEditText.text.toString().isEmpty()

    }

    private fun isEmailValid(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString())
            .matches()

    private fun setError(viewGroup: ViewGroup, error: String?) {
        viewGroup.children.forEach { if (it is TextInputLayout) it.error = error }
    }



}
