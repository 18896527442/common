package com.ll.common.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueguoping
 * @desc: TODO
 * @date 2022/7/1409:59
 */
@Data
public class EasyExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> datas = new ArrayList<>();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        datas.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
