import io.netty.buffer.ByteBuf

import java.io.ByteArrayOutputStream

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created in 21:15 2021/1/18
 * @Modified By:
 */
object BaseSerializerWithoutTypeId extends BaseSerializer {

  def writeShort(byteBuf: ByteBuf, value: Short): Unit = {
    byteBuf.writeShort(value)
  }
  def readShort(byteBuf: ByteBuf): Short = {
    byteBuf.readShort()
  }

  def writeDouble(byteBuf: ByteBuf, value: Double): Unit = {
    byteBuf.writeDouble(value)
  }
  def readDouble(byteBuf: ByteBuf): Double = {
    byteBuf.readDouble()
  }

  def writeFloat(byteBuf: ByteBuf, value: Float): Unit = {
    byteBuf.writeFloat(value)
  }
  def readFloat(byteBuf: ByteBuf): Float = {
    byteBuf.readFloat()
  }

  def writeChar(byteBuf: ByteBuf, value: Char): Unit = {
    writeString(byteBuf, value.toString)
  }
  def readChar(byteBuf: ByteBuf): Char = {
    readString(byteBuf).toCharArray.head
  }

  def writeBoolean(byteBuf: ByteBuf, value: Boolean): Unit = {
    byteBuf.writeBoolean(value)
  }
  def readBoolean(byteBuf: ByteBuf): Boolean = {
    byteBuf.readBoolean()
  }

  def writeInt(byteBuf: ByteBuf, value: Int): Unit = {
    byteBuf.writeInt(value)
  }
  def readInt(byteBuf: ByteBuf): Int = {
    byteBuf.readInt()
  }

  def writeString(byteBuf: ByteBuf, value: String): Unit = {
    val strInBytes: Array[Byte] = value.getBytes
    val length = strInBytes.length
    byteBuf.writeInt(length)
    byteBuf.writeBytes(strInBytes)
  }
  def readString(byteBuf: ByteBuf): String = {
    val length: Int = byteBuf.readInt()
    val bos: ByteArrayOutputStream = new ByteArrayOutputStream()
    byteBuf.readBytes(bos, length)
    bos.toString
  }

  def writeLong(byteBuf: ByteBuf, value: Long): Unit = {
    byteBuf.writeLong(value)
  }

  def readLong(byteBuf: ByteBuf): Long = {
    byteBuf.readLong()
  }
}