import java.lang.reflect.{Field, Type}

import io.netty.buffer.{ByteBuf, Unpooled}

import scala.collection.JavaConversions._
import scala.reflect.runtime.universe.typeTag

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 15:47 2021/1/20
 * @Modified By:
 */
class Ttest{
  val a: Int = 0
  val c: String = "ccc"
  val b: Double = 2.0
//  val map: Map[Int, Int] = Map(1 -> 100)
}

class MystiqueSerializer[T](template: Class[T]) {
  val fields: Array[Field] = template.getFields
  val declaredFields: Array[Field] = template.getDeclaredFields

  val allFields: Array[Field] = {
    fields.foreach(field =>  field.setAccessible(true))
    declaredFields.foreach(field => field.setAccessible(true))
    fields ++ declaredFields
  }

  def serialize(obj: T): Array[Byte] = {
    val byteBuf: ByteBuf = Unpooled.buffer()
    allFields.zip(serializeFuncs).foreach(field_func => {
      val field = field_func._1
      val func = field_func._2
      val fieldValue = field.get(obj)
      func(byteBuf, fieldValue.asInstanceOf[AnyVal])
    })
    val bytes = BaseSerializerWithoutTypeId.`export`(byteBuf)
    byteBuf.release()
    bytes
  }

  def deSerialize(bytes: Array[Byte]): Map[String, Any] = {
    val byteBuf: ByteBuf = Unpooled.wrappedBuffer(bytes)
    allFields.zip(deSerializeFuncs).map(field_func => {
      val fieldName = field_func._1.getName
      val func = field_func._2
      val fieldValue = func(byteBuf)
      (fieldName, fieldValue)
    }).toMap
  }

  declaredFields.map(declaredField => {
    val valName = declaredField.getName
    val valType = declaredField.getType
  })

  val serializeFuncs: Array[(ByteBuf, AnyVal) => Unit] = {
    allFields.map(field => {
      val valueTypeName = field.getType.getTypeName
      val func: (ByteBuf, AnyVal) => Unit = valueTypeName match {
        case "int" => UglyBaseSerializerWithoutTypeId.writeInt(_,  _)
        case "boolean" => UglyBaseSerializerWithoutTypeId.writeBoolean(_, _)
        case "char" => UglyBaseSerializerWithoutTypeId.writeChar(_, _)
        case "float" => UglyBaseSerializerWithoutTypeId.writeFloat(_, _)
        case "double" => UglyBaseSerializerWithoutTypeId.writeDouble(_, _)
        case "long" => UglyBaseSerializerWithoutTypeId.writeLong(_, _)
        case "short" => UglyBaseSerializerWithoutTypeId.writeShort(_, _)
        case "java.lang.String" => UglyBaseSerializerWithoutTypeId.writeString(_, _)
      }
      func
    })
  }

  val deSerializeFuncs: Array[(ByteBuf) => Any] = {
    allFields.map(field => {
      val valueTypeName = field.getType.getTypeName
      val func: ByteBuf => Any = valueTypeName match {
        case "int" => BaseSerializerWithoutTypeId.readInt(_)
        case "boolean" => BaseSerializerWithoutTypeId.readBoolean(_)
        case "char" => BaseSerializerWithoutTypeId.readChar(_)
        case "float" => BaseSerializerWithoutTypeId.readFloat(_)
        case "double" => BaseSerializerWithoutTypeId.readDouble(_)
        case "long" => BaseSerializerWithoutTypeId.readLong(_)
        case "java.lang.String" => BaseSerializerWithoutTypeId.readString(_).asInstanceOf[String]
        case "short" => BaseSerializerWithoutTypeId.readShort(_)
      }
      func
    })
  }

}

object M {
  def main(args: Array[String]): Unit = {
    val testObj = new Ttest
    val serializer: MystiqueSerializer[Ttest] = new MystiqueSerializer(classOf[Ttest])
    Utils.timing((1 to 1000000).foreach( _ => serializer.serialize(testObj)))
    val bytes = serializer.serialize(testObj)
    Utils.timing((1 to 1000000).foreach( _ => serializer.deSerialize(bytes)))
    val result1 = serializer.deSerialize(bytes)

    Utils.timing((1 to 1000000).foreach(_ => serialize(0, "ccc", 2.0)))
    val bytes2 = serialize(0, "ccc", 2.0)
    Utils.timing((1 to 1000000).foreach( _ => deserialize(bytes2)))
    val result2 = deserialize(bytes2)
    println(result1)

  }


  def serialize(a: Any, b: Any, c: Any): Array[Byte] = {
    val byteBuf: ByteBuf = Unpooled.buffer()
    byteBuf.writeInt(a.asInstanceOf[Int])
    BaseSerializerWithoutTypeId.writeString(byteBuf, b.asInstanceOf[String])
    byteBuf.writeDouble(c.asInstanceOf[Double])
    val bytes = BaseSerializerWithoutTypeId.`export`(byteBuf)
    byteBuf.release()
    bytes
  }

  def deserialize(bytes: Array[Byte]): Map[String, Any] = {
    val byteBuf: ByteBuf = Unpooled.wrappedBuffer(bytes)
    val a = byteBuf.readInt()
    val b = BaseSerializerWithoutTypeId.readString(byteBuf)
    val c = byteBuf.readDouble()
    byteBuf.release()
    Map("a" -> a, "b" -> b, "c"->c)
  }




}