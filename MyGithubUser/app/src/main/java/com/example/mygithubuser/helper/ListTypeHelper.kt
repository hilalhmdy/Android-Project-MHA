package com.example.mygithubuser.helper

import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.model.ItemsItem

object ListTypeHelper {
    fun itemsItemToFavorite(itemsItem: ItemsItem): Favorite{
        var user = Favorite()
        if(itemsItem.username.isNullOrEmpty()){
            return user
        }
        else if (itemsItem.avatarUrl.isNullOrEmpty()){
            return user
        }
        else{
            user.nameUser = itemsItem.username
            user.avatars = itemsItem.avatarUrl
            return user
        }
    }

    fun listItemsItemToListUser(list: List<ItemsItem>): List<Favorite>{
        var newArrayList = ArrayList<Favorite>()
        list.forEach{
            newArrayList.add(itemsItemToFavorite(it))
        }
        return  newArrayList.toList()
    }
}