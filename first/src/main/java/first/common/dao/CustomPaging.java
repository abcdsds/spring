package first.common.dao;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

public class CustomPaging extends AbstractPaginationRenderer {
 
	public CustomPaging() {
		firstPageLabel 		= "<li><a href=\"?currentPageNo=1\" class=\"first\">처음으로</a></li>";
        previousPageLabel 	= "<li><a href=\"?currentPageNo={1}\" class=\"prev\">이전으로</a></li>";
        currentPageLabel 	= "<li class=\"active\"><a href=\"?currentPageNo={1}\">{0}</a></li>";
        otherPageLabel 		= "<li><a href=\"?currentPageNo={1}\">{2}</a></li>";
        nextPageLabel	 	= "<li><a href=\"?currentPageNo={1}\" class=\"next\">다음으로</a></li>";
        lastPageLabel 		= "<li><a href=\"?currentPageNo={1}\" class=\"last\">맨뒤로</a></li>";
	}
}
