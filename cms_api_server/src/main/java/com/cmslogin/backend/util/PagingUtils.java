package com.cmslogin.backend.util;

import com.cmslogin.backend.model.PageModel;

public class PagingUtils {

  public static PageModel page(int page, int count) {
    PageModel pageModel = new PageModel();

    int limit = (page - 1) * count;
    int pageIdx = limit + 1;

    pageModel.setPage(page);
    pageModel.setCount(count);
    pageModel.setPageIdx(pageIdx);
    pageModel.setLimit(limit);

    return pageModel;
  }

  public static void setTotalPage(PageModel pageModel) {
    if (pageModel == null) {
      return;
    }
    System.out.print("pageModel.getTotal()" + pageModel.getTotal());
    int totalPage = (int) (Math.ceil(pageModel.getTotal() / (double) pageModel.getCount()));
    pageModel.setTotalPage(totalPage);
  }
}
