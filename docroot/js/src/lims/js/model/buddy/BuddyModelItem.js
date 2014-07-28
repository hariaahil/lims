/**
 * Buddy Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single Buddy.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelItem = Y.Base.create('buddyModelItem', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        // Get JSON representation of current object
        var data = Y.JSON.stringify(this.toJSON());

        switch (action) {
            case 'create':
            case 'update':
                if (options.action === "updatePresence") {
                    this.updatePresence(data, callback);
                }
                break;

            case 'read':
            case 'delete':
                return;

            default:
                callback('Invalid action');
        }
    },

    /**
     * Updates buddy presence
     *
     * @param data
     * @param callback
     * @private
     */
    updatePresence: function (data, callback) {
        // Create settings that contains request url
        var settings = new Y.LIMS.Core.Settings();
        // Do the request
        Y.io(settings.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateBuddyPresence",
                data: data
            },
            on: {
                success: function (id, o) {
                    callback(null, o.response);
                },
                failure: function (x, o) {
                    callback("Cannot update buddy presence", o.response);
                }
            }
        });
    }


}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        buddyId: {
            value: null // default value
        },

        screenName: {
            value: "" // default value
        },

        fullName: {
            value: "" // default value
        },

        presence: {
            value: null // default value
        }
    }
});
