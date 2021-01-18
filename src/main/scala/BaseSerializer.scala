import io.netty.buffer.ByteBuf

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created in 18:28 2021/1/18
 * @Modified By:
 */
trait BaseSerializer {
  def writeInt(byteBuf: ByteBuf, value: Int): Unit
  def readInt(byteBuf: ByteBuf): Int

  def writeShort(byteBuf: ByteBuf, value: Short): Unit
  def readShort(byteBuf: ByteBuf): Short

  def writeDouble(byteBuf: ByteBuf, value: Double): Unit
  def readDouble(byteBuf: ByteBuf): Double

  def writeFloat(byteBuf: ByteBuf, value: Float): Unit
  def readFloat(byteBuf: ByteBuf): Float

  def writeString(byteBuf: ByteBuf, value: String): Unit
  def readString(byteBuf: ByteBuf): String

  def writeChar(byteBuf: ByteBuf, value: Char): Unit
  def readChar(byteBuf: ByteBuf): Char

  def writeBoolean(byteBuf: ByteBuf, value: Boolean): Unit
  def readBoolean(byteBuf: ByteBuf): Boolean
}