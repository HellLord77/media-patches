package app.morphe.patches.epicon.content

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.epicon.extension.sharedExtensionPatch
import app.morphe.patches.epicon.extension.sharedExtensionPatchTv
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON
import app.morphe.patches.epicon.shared.Constants.COMPATIBILITY_EPICON_TV
import app.morphe.patches.shared.morphe.kotlin.Result
import app.morphe.patches.shared.morphe.kotlin.coroutines.Continuation
import app.morphe.patches.shared.morphe.kotlin.coroutines.CoroutineContext
import app.morphe.patches.shared.morphe.okhttp3.HttpUrl
import app.morphe.patches.shared.morphe.okhttp3.Protocol
import app.morphe.patches.shared.morphe.okhttp3.Request
import app.morphe.patches.shared.morphe.okhttp3.internal.http.RealResponseBody
import app.morphe.patches.shared.patches.dex.addGetterMethodPatch
import app.morphe.patches.shared.patches.dex.addSetterMethodPatch
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
import app.morphe.patches.epicon.shared.morphe.com.epicchannel.epicon.apiService.EpicOnApiService as EpicOnApiServiceTv
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

        @Suppress("SpreadOperator")
        dependsOn(
            sharedExtensionPatch,
            addGetterMethodPatch(Retrofit2ResponseRawResponseFieldAccessFingerprint, "raw"),
            addGetterMethodPatch(Retrofit2ResponseBodyFieldAccessFingerprint, "body"),
            addSetterMethodPatch(RequestBuilderUrlFieldAccessFingerprint, "url", isFluent = true),
            addSetterMethodPatch(
                OkHttp3ResponseBuilderCodeFieldAccessFingerprint,
                "code",
                isFluent = true,
            ),
            addSetterMethodPatch(
                OkHttp3ResponseBuilderMessageFieldAccessFingerprint,
                "message",
                isFluent = true,
            ),
            *mapOf(
                "request" to RequestClassFingerprint,
                "protocol" to ProtocolClassFingerprint,
            ).map { (methodName, classFingerprint) ->
                addSetterMethodPatch({
                    Fingerprint(
                        classFingerprint = OkHttp3ResponseBuilderBuildFingerprint,
                        filters =
                            listOf(
                                fieldAccess(
                                    definingClass = "this",
                                    type =
                                        classFingerprint
                                            .matchSingle()
                                            .originalClassDef.type,
                                    opcode = Opcode.IGET_OBJECT,
                                ),
                            ),
                    ).matchSingle().instructionMatches.first().instruction.getReference<FieldReference>()!!
                }, methodName, isFluent = true)
            }.toTypedArray(),
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
            obfuscateExtensionMethodReferencePatch(
                Request.Builder.toString() to "build" to RequestBuilderBuildFingerprint,
                OkHttp3Response.toString() to "isSuccessful" to OkHttp3ResponseIsSuccessfulFingerprint,
                OkHttp3Response.Builder.toString() to "build" to OkHttp3ResponseBuilderBuildFingerprint,
                Retrofit2Response.toString() to "<init>" to Retrofit2ResponseConstructorFingerprint,
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

@Suppress("unused")
val accessContentTvPatch =
    bytecodePatch(
        name = "Access content (TV)",
        description = "Enables access to content.",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_EPICON_TV)

        dependsOn(
            sharedExtensionPatchTv,
            obfuscateExtensionClassPatch(
                Continuation.toString() to ContinuationClassFingerprint,
                Result.Failure.toString() to ResultFailureClassFingerprint,
                EpicOnApiServiceTv.toString() to Show_UrlFingerprint,
            ),
            obfuscateExtensionFieldReferencePatch(),
            obfuscateExtensionMethodReferencePatch(
                EpicOnApiServiceTv.toString() to "show_url" to Show_UrlFingerprint,
            ),
            obfuscateExtensionClassPatch(
                mapOf(CoroutineContext.toString() to { ContinuationGetContextFingerprint.matchSingle().originalMethod.returnType }),
            ),
        )

        execute {
            Show_UrlMethodCallFingerprint.matchAllMethodIndicesForEach {
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
                            "show_url",
                            listOf(reference.definingClass) + reference.parameterTypes,
                            reference.returnType,
                        ),
                    ),
                )
            }
        }
    }
