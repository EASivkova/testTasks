$j(document).ready(function(){
	$j('#loading').remove();
    $j('<select id="publicDates"></select>').appendTo('#content');
		$j.ajax({
			type: "GET",
			url: 'rest/resume/publicDates.json',
			dataType: "json",
			success: function(msg) {
				$j("#publicDates").get(0).options.length = 0;
				$j.each(msg.list, function(index, item) {
					$j("#publicDates").get(0).options[$j("#publicDates").get(0).options.length] = new Option(item.display, item.value);
				});
				resumes.publicDate = $j("#publicDates").get(0).options[0].value;
				$j("#publicDates option[0]").attr("selected", "selected");
				resumes.loadListResume();
			},
			error: function() {
				alert("Failed to load publicDates");
			}
		});
    $j("#publicDates").bind("change", function() {
    	resumes.publicDate = $j(this).val();
    	resumes.loadListResume();
    });
});

var resumes = {
	publicDate: '',
	loadListResume: function () {
		$j('<TABLE id=resumes class=display cellSpacing=0 width="100%"><THEAD><TR><TH>№</TH><TH>Должность</TH><TH>Зарплата</TH><TH>Образование</TH></TR></THEAD></TABLE>').appendTo('#content');
		$j('#resumes').dataTable({
			"ajax": {
	            "url": 'rest/resume/list.json',
	            "dataSrc": "list"
	        },
	        "columns": [
	            { "data": "id" },
	            { "data": "profession" },
	            { "data": "salary" },
	            { "data": "education" }
	        ],
	        "paging": false,
	        "info": false,
			"searching": false,
	        "columnDefs": [{
		        	"targets": [ 1 ],
		            "visible": false,
		            "searchable": false
		        }
		    ],
			"language": {
	            "lengthMenu": "Показывать _MENU_ записей на странице",
	            "zeroRecords": "Ничего не найдено",
	            "info": "Показана страница _PAGE_ из _PAGES_",
	            "infoEmpty": "Записи не найдены",
	            "infoFiltered": "(отфильтровано из _MAX_ записей)"
	        }
		});
		$j('#resumes tbody').on('click', 'td', function () {
			if ($j(this).index() != 3) {
				var tr = $j(this).parents('tr').eq(0)[0];
				var id = $j('td', tr).eq(0).text();
				resume.idResume = id;
				resume.viewResume();
			}
	    });
	}	
};

var resume = {
	idResume: 1,
	viewResume: function () {
	    $j('#content').empty();
	    $j('<input type="text" id="professionresume" name="professionresume" size="100">').appendTo('#content');
	    $j('<input type="text" id="fioresume" name="fioresume" size="100">').appendTo('#content');
	    $j('<input type="text" id="salaryresume" name="salaryresume" size="100">').appendTo('#content');
	    $j('<input type="text" id="ageresume" name="ageresume" size="100">').appendTo('#content');
	    $j('<input type="text" id="educationresume" name="educationresume" size="100">').appendTo('#content');
	    $j('<input type="text" id="contactresume" name="contactresume" size="100">').appendTo('#content');
		$j('<div id="skillsresume" name="skillsresume"></div>').appendTo('#content');
	    if (this.idResume != null) {
		    $j.ajax({
	            type: "GET",
	            url: 'rest/resume/getById.json',
	            data: "idResume=" + this.idResume,
	            dataType: "json",
	            success: function(msg) {
	            	$j('#professionresume').val(msg.profession);
	            	$j('#fioresume').val(msg.fio);
	            	$j('#salaryresume').val(msg.salary);
	            	$j('#ageresume').val(msg.age);
	            	$j('#educationresume').val(msg.education);
	            	$j('#contactresume').val(msg.contact);
	            	msg.skills.appendTo('#skillsresume');
	            },
	            error: function() {
	                alert("Ошибка получения информации по задаче.");
	            }
	        });
		}
	    $j('<button>Вернуться к списку резюме</button>').appendTo('#content')
		.on('click', function () {
		    $j('#content').empty();
		    $j('<select id="publicDates"></select>').appendTo('#content');
			$j.ajax({
				type: "GET",
				url: 'rest/resume/publicDates.json',
				dataType: "json",
				success: function(msg) {
					$j("#publicDates").get(0).options.length = 0;
					$j.each(msg.list, function(index, item) {
						$j("#publicDates").get(0).options[$j("#publicDates").get(0).options.length] = new Option(item.display, item.value);
					});
					resumes.publicDate = $j("#publicDates").get(0).options[0].value;
					$j("#publicDates option[0]").attr("selected", "selected");
					resumes.loadListResume();
				},
				error: function() {
					alert("Failed to load publicDates");
				}
			});
		    $j("#publicDates").bind("change", function() {
		    	resumes.publicDate = $j(this).val();
		    	resumes.loadListResume();
		    });
	        return false;
		});
	},
};
