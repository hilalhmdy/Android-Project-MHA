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
import com.example.mygithubuser.databinding.FragmentFollowBinding
import com.example.mygithubuser.model.FollowResponse


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding?=null
    private val binding get() = _binding!!;

    private lateinit var followViewModel : FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollow.layoutManager = layoutManager
        binding.rvFollow.setHasFixedSize(true)

        val bundle = arguments
        val message = bundle?.getString("username")

        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        followViewModel.userData.observe(viewLifecycleOwner, { userData ->
            setUserList(userData)
        })

        followViewModel.getUserFollowers(message)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserList(items: ArrayList<FollowResponse>) {
        val listReview: ArrayList<FollowResponse> = items
        val adapter = FollowUserAdapter(listReview)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_NAME = "username"
    }


}