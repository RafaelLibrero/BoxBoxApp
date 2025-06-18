package com.boxbox.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.model.VConversation

class ConversationsPagingSource(
    private val api: ApiService,
    private val topicId: Int
) : PagingSource<Int, VConversation>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VConversation> {
        val page = params.key ?: 1

        return try {
            val response = api.getVConversations(page, topicId)
            val conversations = response.conversations.map { it.toDomain() }
            val totalPages = response.registros

            val nextKey = if (page < totalPages) page + 1 else null
            val prevKey = if (page > 1) page - 1 else null

            LoadResult.Page(
                data = conversations,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VConversation>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
