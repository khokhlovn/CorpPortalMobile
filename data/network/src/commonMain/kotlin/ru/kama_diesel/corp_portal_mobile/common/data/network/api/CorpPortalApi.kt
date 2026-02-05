package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.*

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

    suspend fun getArticleDetails(articleId: String): ArticleDetailsResponseData {
        return httpClient.get("article") {
            url {
                parameters.append("post_id", articleId)
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

    suspend fun transferThx(transferThxRequestData: TransferThxRequestData): HttpResponse {
        return httpClient.post("transfer_thx") {
            contentType(type = ContentType.Application.Json)
            setBody(body = transferThxRequestData)
        }
    }
}
