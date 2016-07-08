package cc.koosha.nettyfunctional.hook;

import cc.koosha.nettyfunctional.matched.MatchedInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;


public abstract class RemovedInboundSink<T> extends MatchedInboundHandler<T> {

    @Override
    protected final void unsupportedMsg(final ChannelHandlerContext ctx,
                                        final Object msg) {
        ctx.fireChannelRead(msg);
    }

    @Override
    protected final void read0(final ChannelHandlerContext ctx,
                               final T read) throws Exception {
        try {
            this.read1(ctx, read);
        }
        finally {
            ReferenceCountUtil.release(read);
        }
        ctx.pipeline().remove(this);
    }

    protected abstract void read1(ChannelHandlerContext ctx, T read) throws Exception;

}
