import io.netty.buffer.ByteBuf/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created in 21:16 2021/1/18
 * @Modified By:
 */
object BaseSerializerWithTypeId extends BaseSerializer {
  def readValue(byteBuf: ByteBuf): AnyVal = {
    val typeId = byteBuf.readByte().toInt
    typeId match {
      case TypeId.INT => byteBuf.readInt()
      case TypeId.BOOLEAN => byteBuf.readBoolean()
      case TypeId.CHAR => BaseSerializerWithoutTypeId.readChar(byteBuf)
      case TypeId.DOUBLE => byteBuf.readDouble()
      case TypeId.FLOAT => byteBuf.readFloat()
      case TypeId.LONG =>  byteBuf.readLong()
      case TypeId.STRING => BaseSerializerWithoutTypeId.readString(byteBuf)
    }
  }

  override def writeInt(byteBuf: ByteBuf, value: Int): Unit = {
    byteBuf.writeByte(TypeId.INT)
    byteBuf.writeInt(value)
  }
  override def readInt(byteBuf: ByteBuf): Int = {
    byteBuf.readByte()
    byteBuf.readInt()
  }

  override def writeShort(byteBuf: ByteBuf, value: Short): Unit = {
    byteBuf.writeByte(TypeId.SHORT)
    byteBuf.writeShort(value)
  }
  override def readShort(byteBuf: ByteBuf): Short = {
    byteBuf.readByte()
    byteBuf.readShort()
  }

  override def writeDouble(byteBuf: ByteBuf, value: Double): Unit = {
    byteBuf.writeByte(TypeId.DOUBLE)
    byteBuf.writeDouble(value)
  }
  override def readDouble(byteBuf: ByteBuf): Double = {
    byteBuf.readByte()
    byteBuf.readDouble()
  }

  override def writeFloat(byteBuf: ByteBuf, value: Float): Unit = {
    byteBuf.writeByte(TypeId.FLOAT)
    byteBuf.writeFloat(value)
  }
  override def readFloat(byteBuf: ByteBuf): Float = {
    byteBuf.readByte()
    byteBuf.readFloat()
  }

  override def writeString(byteBuf: ByteBuf, value: String): Unit = {
    byteBuf.writeByte(TypeId.STRING)
    BaseSerializerWithoutTypeId.writeString(byteBuf, value)
  }
  override def readString(byteBuf: ByteBuf): String = {
    byteBuf.readByte()
    BaseSerializerWithoutTypeId.readString(byteBuf)
  }

  override def writeChar(byteBuf: ByteBuf, value: Char): Unit = {
    byteBuf.writeByte(TypeId.CHAR)
    BaseSerializerWithoutTypeId.writeChar(byteBuf, value)
  }
  override def readChar(byteBuf: ByteBuf): Char = {
    byteBuf.readByte()
    BaseSerializerWithoutTypeId.readChar(byteBuf)
  }

  override def writeBoolean(byteBuf: ByteBuf, value: Boolean): Unit = {
    byteBuf.writeByte(TypeId.BOOLEAN)
    byteBuf.writeBoolean(value)
  }
  override def readBoolean(byteBuf: ByteBuf): Boolean = {
    byteBuf.readByte()
    byteBuf.readBoolean()
  }
}
