package com.sopt.now.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.data.remote.response.GetFriendsResponse
import com.sopt.now.data.remote.response.GetUserResponse
import com.sopt.now.databinding.ItemHomeFriendBinding
import com.sopt.now.databinding.ItemHomeMineBinding

class HomeAdapter : ListAdapter<HomeAdapter.HomeItem, RecyclerView.ViewHolder>(HomeDiffCallback()) {
    private var myProfile: GetUserResponse.User = GetUserResponse.User.defaultUser
    private var friendList: List<GetFriendsResponse.Data> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MINE -> MineViewHolder.from(parent)

            VIEW_TYPE_FRIEND -> FriendViewHolder.from(parent)

            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MineViewHolder -> holder.bind(myProfile)
            is FriendViewHolder -> holder.bind(friendList[position - MINE_PROFILE_COUNT])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == MINE_PROFILE_POSITION) {
            VIEW_TYPE_MINE
        } else {
            VIEW_TYPE_FRIEND
        }
    }

    override fun getItemCount(): Int {
        return friendList.size + if (myProfile != GetUserResponse.User.defaultUser) MINE_PROFILE_COUNT else 0
    }

    fun submitList(
        myProfile: GetUserResponse.User,
        friendList: List<GetFriendsResponse.Data>,
    ) {
        this.myProfile = myProfile
        this.friendList = friendList
        submitList(listOf(HomeItem.Mine(myProfile)) + friendList.map { HomeItem.FriendItem(it) })
    }

    class MineViewHolder private constructor(
        private val binding: ItemHomeMineBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetUserResponse.User) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): MineViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeMineBinding.inflate(layoutInflater, parent, false)
                return MineViewHolder(binding)
            }
        }
    }

    class FriendViewHolder private constructor(
        private val binding: ItemHomeFriendBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetFriendsResponse.Data) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): FriendViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeFriendBinding.inflate(layoutInflater, parent, false)
                return FriendViewHolder(binding)
            }
        }
    }

    sealed class HomeItem {
        data class Mine(val userInfo: GetUserResponse.User) : HomeItem()

        data class FriendItem(val friend: GetFriendsResponse.Data) : HomeItem()
    }

    class HomeDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(
            oldItem: HomeItem,
            newItem: HomeItem,
        ): Boolean {
            return when {
                oldItem is HomeItem.Mine && newItem is HomeItem.Mine -> oldItem.userInfo == newItem.userInfo
                oldItem is HomeItem.FriendItem && newItem is HomeItem.FriendItem -> oldItem.friend == newItem.friend
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: HomeItem,
            newItem: HomeItem,
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_MINE = 0
        private const val VIEW_TYPE_FRIEND = 1

        private const val MINE_PROFILE_POSITION = 0
        private const val MINE_PROFILE_COUNT = 1
    }
}
