package io.koosha.nettyfunctional.hook;

import io.koosha.nettyfunctional.matched.MatchedInboundHandler;
import io.koosha.nettyfunctional.nettyfunctions.Matcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;


public abstract class RemovedInboundTransformer<I> extends MatchedInboundHandler<I> {

    protected RemovedInboundTransformer() {
        super();
    }

    protected RemovedInboundTransformer(final Class<?> clazz) {
        super(clazz);
    }

    protected RemovedInboundTransformer(final Matcher matcher) {
        super(matcher);
    }


    @Override
    protected final void unsupportedMsg(final ChannelHandlerContext ctx,
                                        final Object msg) {
        ctx.fireChannelRead(msg);
    }

    @Override
    protected final void read0(final ChannelHandlerContext ctx,
                               final I msg) throws Exception {
        Object result;

        try {
            result = this.read1(ctx, msg);
        }
        finally {
            ReferenceCountUtil.release(msg);
        }

        if (result != null) {
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(result);
        }
    }

    protected abstract Object read1(ChannelHandlerContext ctx, I msg)
            throws Exception;

}
