package app.perdana.indonesia.data.repository

import app.perdana.indonesia.core.extension.addMapRequestBody
import app.perdana.indonesia.core.extension.addToRequestBody
import app.perdana.indonesia.core.utils.NetworkConfig
import app.perdana.indonesia.data.remote.api.ArcherApiService
import app.perdana.indonesia.data.remote.model.LoginRequest
import app.perdana.indonesia.data.remote.model.LoginResponse
import app.perdana.indonesia.data.remote.model.MemberRequest
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

/**
 * Created by ebysofyan on 12/2/19.
 */

class ArcherApiRepository {
    companion object {
        private var instance: ArcherApiRepository? = null
        fun getInstance(): ArcherApiRepository? {
            if (instance == null) {
                synchronized(ArcherApiRepository::class) {
                    instance = ArcherApiRepository()
                }
            }
            return instance
        }
    }

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        val service = NetworkConfig.client.create(ArcherApiService::class.java)
        return service.login(loginRequest)
    }

    suspend fun register(
        memberRequest: MemberRequest,
        photos: MutableList<Pair<String, File?>>
    ): Response<JsonElement> {
        val service = NetworkConfig.client.create(ArcherApiService::class.java)
        val textRequestBody = HashMap<String, RequestBody>()
            .addMapRequestBody("username", memberRequest.user.username.toString())
            .addMapRequestBody("password", memberRequest.user.password.toString())
            .addMapRequestBody("full_name", memberRequest.full_name.toString())
            .addMapRequestBody("phone", memberRequest.phone.toString())
            .addMapRequestBody("gender", memberRequest.gender.toString())
            .addMapRequestBody("address", memberRequest.address.toString())
            .addMapRequestBody(
                "identity_card_number",
                memberRequest.identity_card_number.toString()
            )

        if (memberRequest.club != null) textRequestBody.addMapRequestBody(
            "club",
            memberRequest.club.toString()
        )
        else textRequestBody.addMapRequestBody("satuan", memberRequest.satuan.toString())

        val imagesBody = mutableListOf<MultipartBody.Part>()
        photos.forEach { pairedFile ->
            val imageRequestBody =
                MultipartBody.Part.createFormData(
                    pairedFile.first,
                    pairedFile.second?.name,
                    pairedFile.second?.addToRequestBody()!!
                )
            imagesBody.add(imageRequestBody)
        }
        return service.register(textRequestBody, imagesBody)
    }
}