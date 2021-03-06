package net.book.dupe.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ BookUpdateC2SPacket.class })
public abstract class BookUpdateC2SPacketMixin
{
    private static final String str1;
    private static final String str2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    @Shadow
    private ItemStack book;
    @Inject(at = { @At("RETURN") }, method = { "<init>(Lnet/minecraft/item/ItemStack;ZI)V" })
    public void onInit(final ItemStack book, final boolean signed, final int slot, final CallbackInfo ci) {
        System.out.println("CALLED");
        if (signed && book.getTag().getList("pages", 8).getString(0).equals("DUPE")) {
            final ListTag listTag = new ListTag();
            listTag.addTag(0, (Tag)StringTag.of(BookUpdateC2SPacketMixin.str1));
            for (int i = 1; i < 30; ++i) {
                listTag.addTag(i, (Tag)StringTag.of(str2));
            }
            this.book.putSubTag("pages", (Tag)listTag);
        }
    }

    static {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 21845; ++i) {
            stringBuilder.append('\u0800');
        }
        str1 = stringBuilder.toString();
    }
}

