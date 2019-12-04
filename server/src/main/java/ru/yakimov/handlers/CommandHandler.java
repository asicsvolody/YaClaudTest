/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimov.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.yakimov.Commands;
import ru.yakimov.ProtocolDataType;
import ru.yakimov.utils.MyPackage;


public class CommandHandler extends ChannelInboundHandlerAdapter {


    private MyPackage myPackage;

    ChannelHandlerContext ctx;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        this.ctx = ctx;
        myPackage = ((MyPackage) msg);


        ProtocolDataType type = (myPackage.getType());

        System.out.println(type.getFirstMessageByte());

        if(type.equals(ProtocolDataType.FILE)){
            System.err.println("Get file annotation");
            ctx.fireChannelRead(myPackage);
            return;
        }
    }


    public void writeError(String msg, MyPackage myPackage){
        if(ctx == null) {
            myPackage.disable();
            return;
        }
        myPackage.set(ProtocolDataType.COMMAND, Commands.ERROR, msg.getBytes());
        ctx.write(myPackage);

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
