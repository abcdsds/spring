package first.common.dao;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

public class CustomPaging extends AbstractPaginationRenderer {
 
	public CustomPaging() {
		firstPageLabel 		= "<li><a href=\"?currentPageNo=1\" class=\"first\">ó������</a></li>";
        previousPageLabel 	= "<li><a href=\"?currentPageNo={1}\" class=\"prev\">��������</a></li>";
        currentPageLabel 	= "<li class=\"active\"><a href=\"?currentPageNo={1}\">{0}</a></li>";
        otherPageLabel 		= "<li><a href=\"?currentPageNo={1}\">{2}</a></li>";
        nextPageLabel	 	= "<li><a href=\"?currentPageNo={1}\" class=\"next\">��������</a></li>";
        lastPageLabel 		= "<li><a href=\"?currentPageNo={1}\" class=\"last\">�ǵڷ�</a></li>";
	}
}
