import Ember from 'ember';

export default Ember.TextField.extend({
  placeholder: 'keyword',

  observeInput: function() {
    if (!this.get('holdValue')) {
      var inp = this.get('value');
      this.set('term', inp);
    }
  }.observes('value', 'holdValue'),

  didInsertElement: function(){
    this.$().focus();
    var val = this.get('term');
    this.setProperties({
      activeIndex: -1,
      value: val
    });
  },

});
