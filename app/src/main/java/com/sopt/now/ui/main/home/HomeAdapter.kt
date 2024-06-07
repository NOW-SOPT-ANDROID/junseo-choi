package com.sopt.now.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.data.remote.response.FriendsResponse
import com.sopt.now.data.remote.response.UserResponse
import com.sopt.now.databinding.ItemHomeFriendBinding
import com.sopt.now.databinding.ItemHomeMineBinding

class HomeAdapter : ListAdapter<HomeAdapter.HomeItem, RecyclerView.ViewHolder>(HomeDiffCallback()) {
    private var myProfile: UserResponse.User = UserResponse.User.defaultUser
    private var friends: List<FriendsResponse.Data> = emptyList()

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
        val item = getItem(position)
        when (holder) {
            is MineViewHolder -> if (item is HomeItem.Mine) holder.bind(item.userInfo)
            is FriendViewHolder -> if (item is HomeItem.FriendItem) holder.bind(item.friend)
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
        return friends.size + if (myProfile != UserResponse.User.defaultUser) MINE_PROFILE_COUNT else 0
    }

    fun submitList(
        myProfile: UserResponse.User,
        friendList: List<FriendsResponse.Data>,
    ) {
        this.myProfile = myProfile
        this.friends = friendList
        submitList(listOf(HomeItem.Mine(myProfile)) + friendList.map { HomeItem.FriendItem(it) })
    }

    class MineViewHolder private constructor(
        private val binding: ItemHomeMineBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserResponse.User) {
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
        fun bind(item: FriendsResponse.Data) {
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
        data class Mine(val userInfo: UserResponse.User) : HomeItem()

        data class FriendItem(val friend: FriendsResponse.Data) : HomeItem()
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
