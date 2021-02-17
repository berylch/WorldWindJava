package gov.nasa.worldwind.ogc.gltf;

import java.nio.*;

import gov.nasa.worldwind.avlist.AVListImpl;
import gov.nasa.worldwind.geom.Vec4;

public class GLTFAccessor extends GLTFArray {

    public static final int COMPONENT_BYTE = 5120; // (BYTE) 	1
    public static final int COMPONENT_UNSIGNED_BYTE = 5121; //(UNSIGNED_BYTE) 	1
    public static final int COMPONENT_SHORT = 5122; // (SHORT) 	2
    public static final int COMPONENT_UNSIGNED_SHORT = 5123; // (UNSIGNED_SHORT) 	2
    public static final int COMPONENT_UNSIGNED_INT = 5125; // (UNSIGNED_INT) 	4
    public static final int COMPONENT_FLOAT = 5126; // (FLOAT) 	4

    public enum AccessorType {
        SCALAR, VEC3
    };
    private int bufferView;
    private int byteOffset;
    private int componentType;
    private int count;
    private AccessorType type;
    private double[] max;
    private double[] min;

    public GLTFAccessor(AVListImpl properties) {
        for (String propName : properties.getKeys()) {
            switch (propName) {
                case GLTFParserContext.KEY_BUFFER_VIEW:
                    this.bufferView = GLTFUtil.getInt(properties.getValue(propName));
                    break;
                case GLTFParserContext.KEY_BYTE_OFFSET:
                    this.byteOffset = GLTFUtil.getInt(properties.getValue(propName));
                    break;
                case GLTFParserContext.KEY_COMPONENT_TYPE:
                    this.componentType = GLTFUtil.getInt(properties.getValue(propName));
                    break;
                case GLTFParserContext.KEY_COUNT:
                    this.count = GLTFUtil.getInt(properties.getValue(propName));
                    break;
                case GLTFParserContext.KEY_TYPE:
                    String value = properties.getValue(propName).toString();
                    this.type = AccessorType.valueOf(value);
                    break;
                case GLTFParserContext.KEY_MAX:
                    this.max = GLTFUtil.retrieveDoubleArray((Object[]) properties.getValue(propName));
                    break;
                case GLTFParserContext.KEY_MIN:
                    this.min = GLTFUtil.retrieveDoubleArray((Object[]) properties.getValue(propName));
                    break;
                default:
                    System.out.println("GLTFAccessor: Unsupported " + propName);
                    break;
            }
        }
    }

    private ByteBuffer retrieveByteBuffer(GLTFRoot root) {
        GLTFBufferView view = root.getBufferViewForIdx(this.bufferView);
        byte[] srcBuffer = view.getViewData(root, this.byteOffset);
        ByteBuffer viewBuffer = ByteBuffer.allocate(srcBuffer.length);
        viewBuffer.put(srcBuffer, 0, srcBuffer.length);
        viewBuffer.rewind();
        viewBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return viewBuffer;
    }

    public Vec4[] getCoordBuffer(GLTFRoot root) {
        if (this.type != AccessorType.VEC3) {
            System.out.println("GLTFAccessor: Unsupported type.");
            return null;
        }
        ByteBuffer srcBuffer = this.retrieveByteBuffer(root);
        Vec4[] ret = null;
        switch (this.componentType) {
            case COMPONENT_FLOAT:
                FloatBuffer floatBuffer = srcBuffer.asFloatBuffer();
                ret = new Vec4[this.count];
                for (int i = 0; i < this.count; i++) {
                    float x = floatBuffer.get();
                    float y = floatBuffer.get();
                    float z = floatBuffer.get();
                    ret[i] = new Vec4(x, y, z);
                }
                break;
            default:
                System.out.println("GLTFAccessor: Unsupported buffer component type " + this.componentType);
                break;
        }
        return ret;
    }

    public int[] getBufferIndices(GLTFRoot root) {
        ByteBuffer srcBuffer = this.retrieveByteBuffer(root);
        int[] ret = null;
        switch (this.componentType) {
            case COMPONENT_UNSIGNED_SHORT:
                ShortBuffer shortBuffer = srcBuffer.asShortBuffer();
                ret = new int[this.count];
                for (int i = 0; i < this.count; i++) {
                    ret[i] = shortBuffer.get();
                }
                break;
            case COMPONENT_UNSIGNED_BYTE:
                ret = new int[this.count];
                for (int i = 0; i < this.count; i++) {
                    ret[i] = srcBuffer.get();
                }
                break;
            default:
                System.out.println("GLTFAccessor: Unsupported indices component type " + this.componentType);
                break;
        }
        return ret;
    }
}