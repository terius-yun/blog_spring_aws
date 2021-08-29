var main ={
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });

        $('#btn-update').on('click', function(){
            _this.update();
        });

        $('#btn-delete').on('click', function(){
            _this.delete();
        });

        $('#btn-search').on('click', function(){
            _this.search();
        });
    },
    save: function(){
        if(vali()){
        var data ={
            title : $('#title').val(),
            author : $('#author').val(),
            content : editor.getData(),
            kategorie : $('#kategorie option:selected').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href= '/';
        }).fail(function(error){
//            alert(JSON.stringify(error));
              alert("권한이 없습니다. \n관리자에게 문의하세요 :(\ntingtangting9398@gmail.com");
        })
        }
    },update : function(){
        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },delete : function(){
             var id = $('#id').val();

             $.ajax({
                 type: 'DELETE',
                 url: '/api/v1/posts/'+id,
                 dataType: 'json',
                 contentType: 'application/json; charset=utf-8'
             }).done(function(){
                 alert('글이 삭제되었습니다.');
                 window.location.href = '/';
             }).fail(function(error){
                 alert(JSON.stringify(error));
             });
    },search : function(){
        var keyword = $("#searchKeyword").val();

        if(keyword == ""){
            alert('검색어를 입력해주세요.');
        }else{
            window.location.href = '/view/search?serachKeyword='+keyword;
        }
    }
};

main.init();

function vali(){
    if($('#title').val() == ""){
        alert('제목을 입력해주세요.');
        return false;
    }
    if($('#author').val() == ""){
        alert('작성자를 입력해주세요.');
        return false;
    }if(editor.getData() == ""){
        alert('내용을 입력해주세요.');
        return false;
    }
    if($('#kategorie option:selected').val() == ""){
        alert('카테고리를 설정해주세요.');
        return false;
    }
    return true;
}