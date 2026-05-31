package com.ishland.fabric.rsls.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(ClientPacketListener.class)
public class MixinClientPlayNetworkHandler {
    
    @Dynamic
    @WrapOperation(
        method = "handleSoundEvent",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V")
    )
    private <T extends PacketListener> void handleSoundsAsync(Packet<T> packet, T processor, @Coerce Object executor, Operation<Void> original,
                                                              @Share("rsls$thirdParam") LocalRef<Object> rsls$thirdParam,
                                                              @Share("rsls$originalOp") LocalRef<Operation<Void>> rsls$originalOperation) {
        rsls$originalOperation.set(original);
        rsls$thirdParam.set(executor);
    }
    
    @Dynamic
    @ModifyExpressionValue(method = "handleSoundEvent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;", opcode = Opcodes.GETFIELD))
    private ClientLevel checkWorldExistence(ClientLevel world, ClientboundSoundPacket packet,
                                            @Share("rsls$thirdParam") LocalRef<Object> rsls$thirdParam,
                                            @Share("rsls$originalOp") LocalRef<Operation<Void>> rsls$originalOperation) {
        if (world == null) {
            rsls$originalOperation.get().call(packet, this, rsls$thirdParam.get());
            return null;
        }
        return world;
    }
    
}
