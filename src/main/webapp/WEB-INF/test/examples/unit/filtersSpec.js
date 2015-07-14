/**
 * Created by prima on 05.04.15.
 */
describe('date and time filters', function () {
    beforeEach(function () {
        module('taskbookApp');
    });

    it('has a datestamp filter', inject(function($filter) {
        expect($filter('datestamp')).not.toBeNull();
    }));

    it('has a timestamp filter', inject(function($filter) {
        expect($filter('timestamp')).not.toBeNull();
    }));

    it("datestamp returns correct date format", inject(function (datestampFilter) {
        expect(datestampFilter(1428181668000)).toEqual('Апр 5, 2015');
    }));

    it("timestamp returns correct time format", inject(function (timestampFilter) {
        expect(timestampFilter(1428213901000)).toEqual('09:05:01');
        expect(timestampFilter(1428251741000)).toEqual('19:35:41');
    }));
});