import Ember from "ember";
import table_data_module from "../utils/table-data-module";

export default Ember.Component.extend({
  tagName: 'table',
  list: [],
  columns: [],
  classNames: ['no-wrap'],
  table: null,
  setContent: function () {
    console.log("TABLE COMPONENT - GENERATING PREVIEW");

    var self = this;
    var selection = this.get('preview');
    var datasource = this.get('datasource');
    var fs = document.getElementById("filterSelect");

    if (selection.length > 0) {
      var columns = table_data_module.getColumns(selection, datasource);
      fs.options.length = 0;
      var sk = 0;

      for (var i = 0; i < selection.length; i++) {
        console.log(selection[i].datatype);
        if (selection[i].datatype === "Number") {
          fs.options[sk] = new Option(selection[i].label, i);
          sk += 1;
        }
      }

      table_data_module.getContent(selection, datasource).then(function (content) {

        if (self.get('table')) {
          self.get('table').api().clear().destroy();
          self.$().empty();
        }

        var table = self.$().dataTable({
          responsive: {
            details: {
              type: 'inline'
            }
          },
          "data": content.slice(1),
          "columns": columns,
          "scrollX": true
        });

        self.set('table', table);

        $.fn.dataTable.ext.search.push(
          function (settings, data) {

            if (fs.selectedIndex > -1) {
              var min = parseInt($('#min').val(), 10);

              var max = parseInt($('#max').val(), 10);


              var age = parseFloat(data[fs.options[fs.selectedIndex].value]) || 0; // use data for the fsIndex column

              if (( isNaN(min) && isNaN(max) ) ||
                ( isNaN(min) && age <= max ) ||
                ( min <= age && isNaN(max) ) ||
                ( min <= age && age <= max )) {
                return true;
              }
              return false;
            }
            return true;
          }
        );

        // Event listener to the two range filtering inputs to redraw on input
        $('#min, #max').keyup(function () {
          table.fnDraw();  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        });

        fs.onchange = function () {
          $('#min').val("");
          $('#max').val("");
          table.fnDraw();
        };
      });

    } else {
      if (self.get('table')) {
        self.get('table').api().clear().destroy();
        self.$().empty();
      }
    }
  }.observes('preview.[]').on('didInsertElement')
});
