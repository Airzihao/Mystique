import io.netty.buffer.ByteBuf

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created in 21:15 2021/1/18
 * @Modified By:
 */
object BaseSerializerWithoutTypeId extends BaseSerializer {

  override def writeShort(byteBuf: ByteBuf, value: Short): Unit = {
    byteBuf.writeShort(value)
  }
  override def readShort(byteBuf: ByteBuf): Short = {
    byteBuf.readShort()
  }

  override def writeDouble(byteBuf: ByteBuf, value: Double): Unit = {
    byteBuf.writeDouble(value)
  }
  override def readDouble(byteBuf: ByteBuf): Double = {
    byteBuf.readDouble()
  }

  override def writeFloat(byteBuf: ByteBuf, value: Float): Unit = {
    byteBuf.writeFloat(value)
  }
  override def readFloat(byteBuf: ByteBuf): Float = {
    byteBuf.readFloat()
  }

  override def writeChar(byteBuf: ByteBuf, value: Char): Unit = {
    writeString(byteBuf, value.toString)
  }
  override def readChar(byteBuf: ByteBuf): Char = {
    readString(byteBuf).toCharArray.head
  }

  override def writeBoolean(byteBuf: ByteBuf, value: Boolean): Unit = {
    byteBuf.writeBoolean(value)
  }
  override def readBoolean(byteBuf: ByteBuf): Boolean = {
    byteBuf.readBoolean()
  }

  override def writeInt(byteBuf: ByteBuf, value: Int): Unit = {
    byteBuf.writeInt(value)
  }
  override def readInt(byteBuf: ByteBuf): Int = {
    byteBuf.readInt()
  }



  override def writeString(byteBuf: ByteBuf, value: String): Unit = {
    val strInBytes = value.getBytes
    val length = strInBytes.length
    byteBuf.writeInt(length)
    byteBuf.writeBytes(strInBytes)
  }
  override def readString(byteBuf: ByteBuf): String = {
    val length = byteBuf.readInt()
    val bytes: Array[Byte] = new Array[Byte](length)
    byteBuf.readBytes(bytes)
    bytes.toString
  }

  override def writeLong(byteBuf: ByteBuf, value: Long): Unit = {
    byteBuf.writeLong(value)
  }

  override def readLong(byteBuf: ByteBuf): Long = {
    byteBuf.readLong()
  }
}