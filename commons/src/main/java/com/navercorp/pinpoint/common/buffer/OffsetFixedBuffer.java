/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.common.buffer;

import java.nio.ByteBuffer;

/**
 * @author emeroad
 */
public class OffsetFixedBuffer extends FixedBuffer {

    protected final int startOffset;

    public OffsetFixedBuffer(final byte[] buffer, final int offset) {
        if (buffer == null) {
            throw new NullPointerException("buffer must not be null");
        }
        if (offset < 0) {
            throw new IndexOutOfBoundsException("negative offset:" + offset);
        }
        if (offset > buffer.length) {
            throw new IndexOutOfBoundsException("offset:" + offset + " > buffer.length:" + buffer.length);
        }
        this.buffer = buffer;
        this.offset = offset;
        this.startOffset = offset;
    }

    @Override
    public byte[] getBuffer() {
        if (startOffset == 0 && offset == buffer.length) {
            return this.buffer;
        } else {
            return copyBuffer();
        }
    }

    @Override
    public byte[] copyBuffer() {
        final int length = offset - startOffset;
        final byte[] copy = new byte[length];
        System.arraycopy(buffer, startOffset, copy, 0, length);
        return copy;
    }

    @Override
    public ByteBuffer wrapByteBuffer() {
        final int length = offset - startOffset;
        return ByteBuffer.wrap(this.buffer, startOffset, length);
    }
}
