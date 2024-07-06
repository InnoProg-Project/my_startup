import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

inline fun <reified S : Any, reified R : Any> createAndFillProperties(
    source: S,
    receiverClass: KClass<R>,
    excludedFields: List<String> = emptyList(),
    includedFields: List<String> = emptyList()
): R {
    val receiver = receiverClass.createInstance()
    val receiverProperties = receiver::class.memberProperties
    val sourceProperties = source::class.memberProperties.associateBy { it.name }

    receiverProperties.forEach { receiverProperty ->
        val propertyName = receiverProperty.name
        val sourceProperty = sourceProperties[propertyName]

        // Проверяем условия включения и исключения полей
        val shouldInclude = includedFields.isEmpty() || propertyName in includedFields
        val shouldExclude = propertyName in excludedFields

        if (shouldInclude && !shouldExclude) {
            // Проверяем, что свойство существует в источнике и что типы совпадают
            if (sourceProperty != null && receiverProperty.returnType == sourceProperty.returnType) {
                // Проверяем, что свойство приемника изменяемое
                if (receiverProperty is KMutableProperty<*>) {
                    receiverProperty.isAccessible = true
                    (sourceProperty as KProperty1<S, *>).isAccessible = true

                    // Копируем значение свойства из источника в приемник
                    val value = sourceProperty.get(source)
                    (receiverProperty as KMutableProperty<Any?>).setter.call(receiver, value)
                }
            }
        }
    }

    // Заполняем минимальными значениями по умолчанию оставшиеся обязательные свойства
    receiverProperties.forEach { receiverProperty ->
        if (receiverProperty is KMutableProperty<*> && receiverProperty.getter.call(receiver) == null) {
            val defaultValue = when (receiverProperty.returnType.toString()) {
                "kotlin.Int" -> 0
                "kotlin.Long" -> 0L
                "kotlin.Short" -> 0.toShort()
                "kotlin.Byte" -> 0.toByte()
                "kotlin.Double" -> 0.0
                "kotlin.Float" -> 0.0f
                "kotlin.Boolean" -> false
                "kotlin.Char" -> '\u0000'
                "kotlin.String" -> ""
                "kotlin.collections.List" -> emptyList<Any>()
                "kotlin.collections.Set" -> emptySet<Any>()
                "kotlin.collections.Map" -> emptyMap<Any, Any>()
                else -> null
            }
            (receiverProperty as KMutableProperty<Any?>).setter.call(receiver, defaultValue)
        }
    }

    return receiver
}
