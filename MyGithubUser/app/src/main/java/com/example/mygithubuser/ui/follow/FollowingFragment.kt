package com.example.mygithubuser.ui.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.adapter.FollowUserAdapter
import com.example.mygithubuser.databinding.FragmentFollowingBinding
import com.example.mygithubuser.model.FollowResponse

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding?=null
    private val binding get() = _binding!!;

    private lateinit var followingViewModel : FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val message = bundle?.getString("username")

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.layoutManager = layoutManager
        binding.rvFollowing.setHasFixedSize(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        followingViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        followingViewModel.userData.observe(viewLifecycleOwner, { userData ->
            setUserList(userData)
        })

        followingViewModel.getUserFollowing(message)

    }

    private fun setUserList(items: ArrayList<FollowResponse>) {
        val listReview: ArrayList<FollowResponse> = items
        val adapter = FollowUserAdapter(listReview)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_NAME = "username"
    }

}