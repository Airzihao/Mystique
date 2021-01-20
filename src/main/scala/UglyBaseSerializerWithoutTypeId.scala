import io.netty.buffer.ByteBuf

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 20:02 2021/1/20
 * @Modified By:
 */
object UglyBaseSerializerWithoutTypeId {

  def writeShort(byteBuf: ByteBuf, value: Any): Unit = {
      byteBuf.writeShort(value.asInstanceOf[Short])
    }
  def readShort(byteBuf: ByteBuf): Short = {
      byteBuf.readShort()
    }

    def writeDouble(byteBuf: ByteBuf, value: Any): Unit = {
      byteBuf.writeDouble(value.asInstanceOf[Double])
    }
    def readDouble(byteBuf: ByteBuf): Double = {
      byteBuf.readDouble()
    }

    def writeFloat(byteBuf: ByteBuf, value: Any): Unit = {
      byteBuf.writeFloat(value.asInstanceOf[Float])
    }
    def readFloat(byteBuf: ByteBuf): Float = {
      byteBuf.readFloat()
    }

    def writeChar(byteBuf: ByteBuf, value: Any): Unit = {
      writeString(byteBuf, value.toString)
    }
    def readChar(byteBuf: ByteBuf): Char = {
      readString(byteBuf).toCharArray.head
    }

    def writeBoolean(byteBuf: ByteBuf, value: Any): Unit = {
      byteBuf.writeBoolean(value.asInstanceOf[Boolean])
    }
    def readBoolean(byteBuf: ByteBuf): Boolean = {
      byteBuf.readBoolean()
    }

    def writeInt(byteBuf: ByteBuf, value: Any): Unit = {
      byteBuf.writeInt(value.asInstanceOf[Int])
    }
    def readInt(byteBuf: ByteBuf): Int = {
      byteBuf.readInt()
    }



    def writeString(byteBuf: ByteBuf, value: Any): Unit = {
      val strInBytes = value.asInstanceOf[String].getBytes
      val length = strInBytes.length
      byteBuf.writeInt(length)
      byteBuf.writeBytes(strInBytes)
    }
    def readString(byteBuf: ByteBuf): String = {
      val length = byteBuf.readInt()
      val bytes: Array[Byte] = new Array[Byte](length)
      byteBuf.readBytes(bytes)
      bytes.toString
    }

    def writeLong(byteBuf: ByteBuf, value: Any): Unit = {
      byteBuf.writeLong(value.asInstanceOf[Long])
    }

    def readLong(byteBuf: ByteBuf): Long = {
      byteBuf.readLong()
    }
  }
