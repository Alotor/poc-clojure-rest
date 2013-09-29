require(['backbone'], function(Backbone) {
    var Task = Backbone.Model.extend({
    });
    var Tasks = Backbone.Collection.extend({
        url: '/tasks',
        model: Task
    });
    var TasksView = Backbone.View.extend({
        el: "#tasks",
        render: function() {
            var self = this;
            this.collection.map(function(model) {
                self.$el.append("<li>" +  model.get("title") + "</li>");
            });
        }
    });
    var ts = new Tasks();
    var tsView = new TasksView({ collection: ts });
    ts.fetch().done(function() { tsView.render(); });
});
