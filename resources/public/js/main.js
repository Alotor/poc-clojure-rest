require(['backbone'], function(Backbone) {
    var Task = Backbone.Model.extend({
        urlRoot: '/tasks'
    });
    var Tasks = Backbone.Collection.extend({
        url: '/tasks',
        model: Task
    });
    var TasksView = Backbone.View.extend({
        el: '#tasks',
        events: {
            'click input[type=checkbox]': 'toggleCompleted',
            'click #new-task': 'newTask'
        },
        template: _.template('<li><input type="checkbox" class="<%= cid %>" <%= completed %>"><%= title %></input></li>'),

        initialize: function(options) {
            this.collection.on('add', _.bind(this.renderTask, this));
        },

        renderTask: function(model) {
            var completed =  model.get('completed') ? ' checked="checked" ' : '',
                list = this.$el.find('#task-list');
            list.append(this.template({
                cid: model.cid,
                title: model.get('title'),
                completed: completed
            }));
        },

        toggleCompleted: function(event) {
            var input = $(event.target),
                cid = input.attr('class'),
                task = this.collection.get(cid);
            task.set('completed', input.is(':checked'));
            task.save();
        },

        newTask: function(event) {
            event.preventDefault();
            var input = this.$el.find('input[type=text]'),
                title = input.val();
            if (title) {
                this.collection.create({title: title, completed: false});
                input.val('');
            }
        }
    });
    var ts = new Tasks();
    var tsView = new TasksView({ collection: ts });
    ts.fetch();
});
