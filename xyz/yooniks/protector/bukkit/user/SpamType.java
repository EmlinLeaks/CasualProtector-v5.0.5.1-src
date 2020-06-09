/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.user;

public enum SpamType {
    ABILITIES,
    ARM_ANIMATION,
    BLOCK_PLACE,
    BLOCK_PLACE_BOOKS,
    CUSTOM_PAYLOAD,
    CUSTOM_PAYLOAD_OTHER,
    POSITION,
    BLOCK_DIG,
    KEEP_ALIVE,
    SET_CREATIVE_SLOT,
    USE_ITEM,
    USE_ENTITY,
    WINDOW_CLICK,
    WINDOW_CLICK_WRONG,
    ENTITY_ACTION,
    SET_CREATIVE_SLOT_WRONG,
    TAB_COMPLETE,
    UPDATE_SIGN,
    TRANSACTION,
    SETTINGS,
    CLOSE_WINDOW,
    RESOURCE_PACK_STATUS,
    BLOCK_PLACE_WRONG,
    RECIPE_DISPLAYED,
    AUTO_RECIPE,
    PAYLOAD_ITEM_ADV,
    PAYLOAD_ITEM_ADV_WRONG;
    

    public static SpamType byName(String a) {
        SpamType[] arrspamType = SpamType.values();
        int n = arrspamType.length;
        int n2 = 0;
        while (n2 < n) {
            SpamType spamType = arrspamType[n2];
            if (spamType.name().equalsIgnoreCase((String)a)) {
                return spamType;
            }
            ++n2;
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    private /* synthetic */ SpamType() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException
        // org.benf.cfr.reader.bytecode.analysis.variables.VariableFactory.localVariable(VariableFactory.java:53)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.mkRetrieve(Op02WithProcessedDataAndRefs.java:931)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.createStatement(Op02WithProcessedDataAndRefs.java:979)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.access$100(Op02WithProcessedDataAndRefs.java:56)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs$11.call(Op02WithProcessedDataAndRefs.java:2060)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs$11.call(Op02WithProcessedDataAndRefs.java:2057)
        // org.benf.cfr.reader.util.graph.AbstractGraphVisitorFI.process(AbstractGraphVisitorFI.java:60)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.convertToOp03List(Op02WithProcessedDataAndRefs.java:2069)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:342)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:901)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$7.run(MainViewerGUI.java:1287)
        throw new IllegalStateException("Decompilation failed");
    }
}

