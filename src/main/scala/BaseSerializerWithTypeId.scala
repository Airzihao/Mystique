import io.netty.buffer.ByteBuf/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created in 21:16 2021/1/18
 * @Modified By:
 */
object BaseSerializerWithTypeId extends BaseSerializer {
  def readValue(byteBuf: ByteBuf): Any = {
    val typeId = byteBuf.readByte().toInt
    typeId match {
      case TypeId.INT => byteBuf.readInt()
      case TypeId.SHORT => byteBuf.readShort()
      case TypeId.BOOLEAN => byteBuf.readBoolean()
      case TypeId.CHAR => BaseSerializerWithoutTypeId.readChar(byteBuf)
      case TypeId.DOUBLE => byteBuf.readDouble()
      case TypeId.FLOAT => byteBuf.readFloat()
      case TypeId.LONG =>  byteBuf.readLong()
      case TypeId.STRING => BaseSerializerWithoutTypeId.readString(byteBuf)
      case TypeId.SEQ_OF => readSeq(byteBuf)
      case TypeId.SET_OF => readSet(byteBuf)
      case TypeId.MAP_OF => readMap(byteBuf)
      case _ => throw new Exception(s"Unsupported Bytes Array.")
    }
  }

  def writeInt(byteBuf: ByteBuf, value: Int): Unit = {
    byteBuf.writeByte(TypeId.INT)
    byteBuf.writeInt(value)
  }

  def writeShort(byteBuf: ByteBuf, value: Short): Unit = {
    byteBuf.writeByte(TypeId.SHORT)
    byteBuf.writeShort(value)
  }

  def writeDouble(byteBuf: ByteBuf, value: Double): Unit = {
    byteBuf.writeByte(TypeId.DOUBLE)
    byteBuf.writeDouble(value)
  }

  def writeFloat(byteBuf: ByteBuf, value: Float): Unit = {
    byteBuf.writeByte(TypeId.FLOAT)
    byteBuf.writeFloat(value)
  }

  def writeString(byteBuf: ByteBuf, value: String): Unit = {
    byteBuf.writeByte(TypeId.STRING)
    BaseSerializerWithoutTypeId.writeString(byteBuf, value)
  }

  def writeChar(byteBuf: ByteBuf, value: Char): Unit = {
    byteBuf.writeByte(TypeId.CHAR)
    BaseSerializerWithoutTypeId.writeChar(byteBuf, value)
  }

  def writeBoolean(byteBuf: ByteBuf, value: Boolean): Unit = {
    byteBuf.writeByte(TypeId.BOOLEAN)
    byteBuf.writeBoolean(value)
  }

  def writeLong(byteBuf: ByteBuf, value: Long): Unit = {
    byteBuf.writeByte(TypeId.LONG)
    byteBuf.writeLong(value)
  }

  def writeSeq(byteBuf: ByteBuf, seq: Seq[Any]): Unit = {
    byteBuf.writeByte(TypeId.SEQ_OF)
    byteBuf.writeInt(seq.length)
    seq.foreach(item => write(byteBuf, item))
  }

  def writeSet(byteBuf: ByteBuf, set: Set[Any]): Unit = {
    byteBuf.writeByte(TypeId.SET_OF)
    byteBuf.writeInt(set.size)
    set.foreach(item => write(byteBuf, item))
  }

  def writeMap(byteBuf: ByteBuf, map: Map[Any, Any]): Unit = {
    byteBuf.writeByte(TypeId.MAP_OF)
    byteBuf.writeInt(map.size)
    map.foreach(kv => {
      write(byteBuf, kv._1)
      write(byteBuf, kv._2)
    })
  }

  def readSeq(byteBuf: ByteBuf): Seq[Any] = {
    val itemCount = byteBuf.readInt()
    (1 to itemCount).map(_ => readValue(byteBuf)).toList
  }

  def readSet(byteBuf: ByteBuf): Set[Any] = {
    val itemCount = byteBuf.readInt()
    (1 to itemCount).map(_ => readValue(byteBuf)).toSet
  }

  def readMap(byteBuf: ByteBuf): Map[Any, Any] = {
    val kvCount = byteBuf.readInt()
    (1 to kvCount).map(_ => {
      val k = readValue(byteBuf)
      val v = readValue(byteBuf)
      (k, v)
    }).toMap
  }

  def write(byteBuf: ByteBuf, value: Any): Unit = {
    value match {
      case s: Int => writeInt(byteBuf, value.asInstanceOf[Int])
      case s: Short => writeShort(byteBuf, value.asInstanceOf[Short])
      case s: Long => writeLong(byteBuf, value.asInstanceOf[Long])
      case s: Double => writeDouble(byteBuf, value.asInstanceOf[Double])
      case s: Float => writeFloat(byteBuf, value.asInstanceOf[Float])
      case s: Char => writeChar(byteBuf, value.asInstanceOf[Char])
      case s: Boolean => writeBoolean(byteBuf, value.asInstanceOf[Boolean])
      case s: String => writeString(byteBuf, value.toString)
      case s: Seq[_] => writeSeq(byteBuf, value.asInstanceOf[Seq[Any]])
      case s: Set[_] => writeSet(byteBuf, value.asInstanceOf[Set[Any]])
      case s: Map[_, _] => writeMap(byteBuf, value.asInstanceOf[Map[Any, Any]])
      case _ => throw new Exception(s"The value $value is not a supported type.")
    }
  }

}
