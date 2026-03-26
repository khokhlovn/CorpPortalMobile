package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.AddToCartRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ArticleDetailsResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ArticlesResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.CancelOrderRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.CartResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.CommentLikeRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.DropCartItemRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LikeRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LoginRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.MeResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.OrdersListResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.PhoneDirectoryResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ProfileResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ReservationListResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.SendCommentRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ShopListResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TagsResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ThxHistoryResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TopWorkersListResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TransferThxRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TransferThxResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.UpdateCartItemRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.UserIdsResponseData

@Inject
class CorpPortalApi(
    val httpClient: HttpClient,
) {

    suspend fun login(loginRequestData: LoginRequestData): HttpResponse {
        return httpClient.post("login") {
            contentType(type = ContentType.Application.Json)
            setBody(body = loginRequestData)
        }
    }

    suspend fun getArticles(page: Int, fromDate: Long?, toDate: Long?, selectedTagsIds: List<String>): ArticlesResponseData {
        return httpClient.get("articles") {
            url {
                page.let {
                    parameters.append("page", it.toString())
                }
                fromDate?.let {
                    parameters.append("start", it.toString())
                }
                toDate?.let {
                    parameters.append("finish", it.toString())
                }
                selectedTagsIds.forEach {
                    parameters.append("tag_id", it)
                }
            }
        }.body()
    }

    suspend fun getTags(): TagsResponseData {
        return httpClient.get("tags").body()
    }

    suspend fun getArticleDetails(articleId: String, userId: String): ArticleDetailsResponseData {
        return httpClient.get("article") {
            url {
                parameters.append("post_id", articleId)
                parameters.append("user_id", userId)
            }
        }.body()
    }

    suspend fun sendComment(sendCommentRequestData: SendCommentRequestData): HttpResponse {
        return httpClient.post("comment") {
            contentType(type = ContentType.Application.Json)
            setBody(body = sendCommentRequestData)
        }
    }

    suspend fun like(likeRequestData: LikeRequestData): HttpResponse {
        return httpClient.post("like") {
            contentType(type = ContentType.Application.Json)
            setBody(body = likeRequestData)
        }
    }

    suspend fun getShopList(): ShopListResponseData {
        return httpClient.get("shop_list").body()
    }

    suspend fun addToCart(addToCartRequestData: AddToCartRequestData): HttpResponse {
        return httpClient.post("add_cart_item") {
            contentType(type = ContentType.Application.Json)
            setBody(body = addToCartRequestData)
        }
    }

    suspend fun updateCartItem(updateCartItemRequestData: UpdateCartItemRequestData): HttpResponse {
        return httpClient.post("update_cart_item") {
            contentType(type = ContentType.Application.Json)
            setBody(body = updateCartItemRequestData)
        }
    }

    suspend fun dropCartItem(dropCartItemRequestData: DropCartItemRequestData): HttpResponse {
        return httpClient.post("drop_cart_item") {
            contentType(type = ContentType.Application.Json)
            setBody(body = dropCartItemRequestData)
        }
    }

    suspend fun getCartData(): CartResponseData {
        return httpClient.get("cart_data").body()
    }

    suspend fun getMyInfo(): MeResponseData {
        return httpClient.get("me").body()
    }

    suspend fun makeOrder(): HttpResponse {
        return httpClient.post("order")
    }

    suspend fun getOrders(): OrdersListResponseData {
        return httpClient.get("orders_list").body()
    }

    suspend fun cancelOrder(cancelOrderRequestData: CancelOrderRequestData): HttpResponse {
        return httpClient.post("cart/cancel") {
            contentType(type = ContentType.Application.Json)
            setBody(body = cancelOrderRequestData)
        }
    }

    suspend fun getPhoneBook(): PhoneDirectoryResponseData {
        return httpClient.get("phone_book").body()
    }

    suspend fun getReservationList(start: Long?, finish: Long?): ReservationListResponseData {
        return httpClient.get("reservation_list") {
            url {
                start?.let {
                    parameters.append("start", it.toString())
                }
                finish?.let {
                    parameters.append("finish", it.toString())
                }
            }
        }.body()
    }

    suspend fun getProfile(): ProfileResponseData {
        return httpClient.get("profile").body()
    }

    suspend fun getTopWorkers(): TopWorkersListResponseData {
        return httpClient.get("top_workers").body()
    }

    suspend fun getUserIds(): UserIdsResponseData {
        return httpClient.get("user_ids").body()
    }

    suspend fun transferThx(transferThxRequestData: TransferThxRequestData): TransferThxResponseData {
        return httpClient.post("transfer_thx") {
            contentType(type = ContentType.Application.Json)
            setBody(body = transferThxRequestData)
        }.body()
    }

    suspend fun transferThxCeo(transferThxRequestData: TransferThxRequestData): TransferThxResponseData {
        return httpClient.post("transfer_thx_by_ceo") {
            contentType(type = ContentType.Application.Json)
            setBody(body = transferThxRequestData)
        }.body()
    }

    suspend fun getThxHistory(): ThxHistoryResponseData {
        return httpClient.get("thx_history").body()
    }

    suspend fun getWeeklyThx(): HttpResponse {
        return httpClient.post("get_weekly_thx")
    }

    suspend fun commentLike(commentLikeRequestData: CommentLikeRequestData): HttpResponse {
        return httpClient.post("comment_like") {
            contentType(type = ContentType.Application.Json)
            setBody(body = commentLikeRequestData)
        }
    }
}
