package app.morphe.patches.epicon.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.StringComparisonType
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall
import app.morphe.patcher.string
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.data.apiService.EpicOnApiService
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.data.model.videoURL.PlayUrlInputData
import app.morphe.patches.shared.Type
import app.morphe.patches.shared.isNotExtension
import app.morphe.patches.shared.morphe.java.lang.Object
import app.morphe.patches.shared.morphe.java.lang.String
import app.morphe.patches.shared.morphe.java.net.URI
import app.morphe.patches.shared.morphe.kotlin.Result
import app.morphe.patches.shared.morphe.kotlin.coroutines.Continuation
import app.morphe.patches.shared.morphe.kotlin.coroutines.CoroutineContext
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.model.videoURL.PlayUrlInputData as PlayUrlInputDataTv

object ShowURLMethodCallFingerprint : Fingerprint(
    filters =
        listOf(
            methodCall(
                definingClass = EpicOnApiService.toString(),
                name = "showURL",
                parameters = listOf(PlayUrlInputData.toString(), "Lkotlin/coroutines/"),
                returnType = Object.toString(),
                opcode = Opcode.INVOKE_INTERFACE,
            ),
        ),
    custom = ::isNotExtension,
)

object ContinuationClassFingerprint : Fingerprint(
    name = "resumeWith",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
    returnType = Type.void,
    parameters = listOf(Object.toString()),
    custom = { _, classDef -> classDef.type != Continuation.toString() },
)

object ContinuationGetContextFingerprint : Fingerprint(
    name = "getContext",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
    returnType = "L",
    parameters = emptyList(),
    custom = { _, classDef -> classDef.type != Continuation.toString() },
)

object CoroutineContextClassFingerprint : Fingerprint(
    definingClass = "Lkotlin/coroutines/",
    name = "fold",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
    returnType = Object.toString(),
    parameters = listOf(Object.toString(), "Lkotlin/jvm/functions/"),
    custom = { _, classDef -> !classDef.type.startsWith(CoroutineContext.toString().dropLast(1)) },
)

object ResultFailureClassFingerprint : Fingerprint(
    name = "toString",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = String.toString(),
    parameters = emptyList(),
    strings = listOf("Failure("),
    custom = { _, classDef -> classDef.type != Result.Failure.toString() },
)

object RealResponseBodyClassFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.SYNTHETIC, AccessFlags.CONSTRUCTOR),
    returnType = Type.void,
    parameters = listOf(Object.toString(), Type.long, "Lokio/", Type.int),
)

object HttpUrlClassFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = URI.toString(),
    parameters = emptyList(),
)

object ProtocolClassFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    name = "<clinit>",
    accessFlags = listOf(AccessFlags.STATIC, AccessFlags.CONSTRUCTOR),
    returnType = Type.void,
    parameters = emptyList(),
    strings = listOf("http/1.0", "http/1.1"),
)

object RequestClassFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    name = "toString",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = String.toString(),
    parameters = emptyList(),
    filters = listOf(string("Request{", StringComparisonType.STARTS_WITH)),
)

object OkHttp3ResponseClassFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    name = "toString",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = String.toString(),
    parameters = emptyList(),
    filters = listOf(string("Response{", StringComparisonType.STARTS_WITH)),
)

object Retrofit2ResponseClassFingerprint : Fingerprint(
    definingClass = "Lretrofit2/",
    name = "toString",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = String.toString(),
    parameters = emptyList(),
    filters =
        listOf(
            methodCall(
                definingClass = "Lokhttp3/",
                name = "toString",
                parameters = emptyList(),
                returnType = String.toString(),
                opcode = Opcode.INVOKE_VIRTUAL,
            ),
        ),
)

object RequestBuilderUrlFieldAccessFingerprint : Fingerprint(
    classFingerprint = RequestBuilderBuildFingerprint,
    filters =
        listOf(
            fieldAccess(
                definingClass = "this",
                type = "Lokhttp3/",
                opcode = Opcode.IGET_OBJECT,
            ),
        ),
)

object OkHttp3ResponseBuilderCodeFieldAccessFingerprint : Fingerprint(
    classFingerprint = OkHttp3ResponseBuilderBuildFingerprint,
    filters =
        listOf(
            fieldAccess(
                definingClass = "this",
                type = Type.int,
                opcode = Opcode.IPUT,
            ),
        ),
)

object OkHttp3ResponseBuilderMessageFieldAccessFingerprint : Fingerprint(
    classFingerprint = OkHttp3ResponseBuilderBuildFingerprint,
    filters =
        listOf(
            fieldAccess(
                definingClass = "this",
                type = String.toString(),
                opcode = Opcode.IGET_OBJECT,
            ),
        ),
)

object Retrofit2ResponseRawResponseFieldAccessFingerprint : Fingerprint(
    classFingerprint = Retrofit2ResponseConstructorFingerprint,
    filters =
        listOf(
            fieldAccess(
                definingClass = "this",
                type = "Lokhttp3/",
                opcode = Opcode.IGET_OBJECT,
            ),
        ),
)

object Retrofit2ResponseBodyFieldAccessFingerprint : Fingerprint(
    classFingerprint = Retrofit2ResponseConstructorFingerprint,
    filters =
        listOf(
            fieldAccess(
                definingClass = "this",
                type = Object.toString(),
                opcode = Opcode.IPUT_OBJECT,
            ),
        ),
)

object RequestBuilderBuildFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Lokhttp3/",
    parameters = emptyList(),
    strings = listOf("url == null"),
)

object OkHttp3ResponseIsSuccessfulFingerprint : Fingerprint(
    classFingerprint = OkHttp3ResponseClassFingerprint,
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = Type.boolean,
    parameters = emptyList(),
)

object OkHttp3ResponseBuilderBuildFingerprint : Fingerprint(
    definingClass = "Lokhttp3/",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Lokhttp3/",
    parameters = emptyList(),
    strings = listOf("request == null", "protocol == null", "message == null"),
)

object Retrofit2ResponseConstructorFingerprint : Fingerprint(
    definingClass = "Lretrofit2/",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    returnType = Type.void,
    parameters = listOf("Lokhttp3/", Object.toString(), "Lokhttp3/"),
)

@Suppress("ktlint:standard:class-naming")
object Show_UrlMethodCallFingerprint : Fingerprint(
    filters =
        listOf(
            methodCall(
                parameters = listOf(PlayUrlInputDataTv.toString(), "L"),
                returnType = Object.toString(),
                opcode = Opcode.INVOKE_INTERFACE,
            ),
        ),
    custom = ::isNotExtension,
)

@Suppress("ktlint:standard:class-naming")
object Show_UrlFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.ABSTRACT),
    returnType = Object.toString(),
    parameters = listOf(PlayUrlInputDataTv.toString(), "L"),
    custom = { method, _ ->
        method.annotations
            .flatMap { it.elements }
            .any { it.value.toString().endsWith("/index/pages/show_url\"") }
    },
)
