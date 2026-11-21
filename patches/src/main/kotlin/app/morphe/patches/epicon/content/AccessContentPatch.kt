package app.morphe.patches.epicon.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.epicon.extension.sharedExtensionPatch
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON
import app.morphe.patches.shared.morphe.kotlin.Result
import app.morphe.patches.shared.morphe.kotlin.coroutines.Continuation
import app.morphe.patches.shared.morphe.kotlin.coroutines.CoroutineContext
import app.morphe.patches.shared.morphe.okhttp3.HttpUrl
import app.morphe.patches.shared.morphe.okhttp3.Protocol
import app.morphe.patches.shared.morphe.okhttp3.Request
import app.morphe.patches.shared.morphe.okhttp3.internal.http.RealResponseBody
import app.morphe.patches.shared.patches.dex.obfuscateExtensionClassPatch
import app.morphe.patches.shared.patches.dex.obfuscateExtensionFieldReferencePatch
import app.morphe.patches.shared.patches.dex.obfuscateExtensionMethodReferencePatch
import app.morphe.util.getReference
import app.morphe.util.matchAllMethodIndicesForEach
import app.morphe.util.matchSingle
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction35c
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableMethodReference
import app.morphe.patches.shared.morphe.okhttp3.Response as OkHttp3Response
import app.morphe.patches.shared.morphe.retrofit2.Response as Retrofit2Response

private const val EXTENSION_CLASS = "Lapp/morphe/extension/epicon/patches/AccessContentPatch;"

@Suppress("unused")
val accessContentPatch =
    bytecodePatch(
        name = "Access content",
        description = "Enables access to content.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_EPICON)

        dependsOn(
            sharedExtensionPatch,
            obfuscateExtensionClassPatch(
                Continuation.toString() to ContinuationClassFingerprint,
                CoroutineContext.toString() to CoroutineContextClassFingerprint,
                Result.Failure.toString() to ResultFailureClassFingerprint,
                RealResponseBody.toString() to RealResponseBodyClassFingerprint,
                HttpUrl.toString() to HttpUrlClassFingerprint,
                Protocol.toString() to ProtocolClassFingerprint,
                Request.toString() to RequestClassFingerprint,
                Request.Builder.toString() to RequestBuilderBuildFingerprint,
                OkHttp3Response.toString() to OkHttp3ResponseClassFingerprint,
                OkHttp3Response.Builder.toString() to OkHttp3ResponseBuilderBuildFingerprint,
                Retrofit2Response.toString() to Retrofit2ResponseClassFingerprint,
            ),
            obfuscateExtensionFieldReferencePatch(
                Request.Builder.toString() to "url" to RequestBuilderUrlFieldAccessFingerprint,
                OkHttp3Response.Builder.toString() to "code" to OkHttp3ResponseBuilderCodeFieldAccessFingerprint,
                OkHttp3Response.Builder.toString() to "message" to OkHttp3ResponseBuilderMessageFieldAccessFingerprint,
                Retrofit2Response.toString() to "rawResponse" to Retrofit2ResponseRawResponseFieldAccessFingerprint,
                Retrofit2Response.toString() to "body" to Retrofit2ResponseBodyFieldAccessFingerprint,
            ),
            obfuscateExtensionMethodReferencePatch(
                Request.Builder.toString() to "build" to RequestBuilderBuildFingerprint,
                OkHttp3Response.toString() to "isSuccessful" to OkHttp3ResponseIsSuccessfulFingerprint,
                OkHttp3Response.Builder.toString() to "build" to OkHttp3ResponseBuilderBuildFingerprint,
                Retrofit2Response.toString() to "<init>" to Retrofit2ResponseConstructorFingerprint,
            ),
            obfuscateExtensionFieldReferencePatch(
                mapOf(
                    OkHttp3Response.Builder.toString() to
                        mapOf(
                            "request" to RequestClassFingerprint,
                            "protocol" to ProtocolClassFingerprint,
                        ).mapValues {
                            {
                                Fingerprint(
                                    classFingerprint = OkHttp3ResponseBuilderBuildFingerprint,
                                    filters =
                                        listOf(
                                            fieldAccess(
                                                definingClass = "this",
                                                type =
                                                    it.value
                                                        .matchSingle()
                                                        .originalClassDef.type,
                                                opcode = Opcode.IGET_OBJECT,
                                            ),
                                        ),
                                ).matchSingle()
                                    .instructionMatches
                                    .first()
                                    .instruction
                                    .getReference<FieldReference>()!!
                                    .name
                            }
                        },
                ),
            ),
        )

        execute {
            ShowURLMethodCallFingerprint.matchAllMethodIndicesForEach {
                val instruction = getInstruction<FiveRegisterInstruction>(it)
                val reference = instruction.getReference<MethodReference>()!!

                replaceInstruction(
                    it,
                    BuilderInstruction35c(
                        Opcode.INVOKE_STATIC,
                        instruction.registerCount,
                        instruction.registerC,
                        instruction.registerD,
                        instruction.registerE,
                        instruction.registerF,
                        instruction.registerG,
                        ImmutableMethodReference(
                            EXTENSION_CLASS,
                            reference.name,
                            listOf(reference.definingClass) + reference.parameterTypes,
                            reference.returnType,
                        ),
                    ),
                )
            }
        }
    }
