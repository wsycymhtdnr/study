1、Android自定义权限级别
值	含义
“normal”	默认值。具有较低风险的权限，此类权限允许请求授权的应用访问隔离的应用级功能，对其他应用、系统或用户的风险非常小。系统会自动向在安装时请求授权的应用授予此类权限，无需征得用户的明确许可（但用户始终可以选择在安装之前查看这些权限）。
“dangerous”	具有较高风险的权限，此类权限允许请求授权的应用访问用户私人数据或获取可对用户造成不利影响的设备控制权。由于此类权限会带来潜在风险，因此系统可能不会自动向请求授权的应用授予此类权限。例如，应用请求的任何危险权限都可能会向用户显示并且获得确认才会继续执行操作，或者系统会采取一些其他方法来避免用户自动允许使用此类功能。
“signature”	只有在请求授权的应用使用与声明权限的应用相同的证书进行签名时系统才会授予的权限。如果证书匹配，则系统会在不通知用户或征得用户明确许可的情况下自动授予权限。
“signatureOrSystem”	
"signature|privileged" 的旧同义词。在 API 级别 23 中已弃用。

系统仅向位于 Android 系统映像的专用文件夹中的应用或使用与声明权限的应用相同的证书进行签名的应用授予的权限。不要使用此选项，因为 signature 保护级别应足以满足大多数需求，无论应用安装在何处，该保护级别都能正常发挥作用。“signatureOrSystem”权限适用于以下特殊情况：多个供应商将应用内置到一个系统映像中，并且需要明确共享特定功能，因为这些功能是一起构建的。

2、预置路径下的apk会自动安装 odex

3、priv-app下会拿到privileged权限

4、priv-app保活