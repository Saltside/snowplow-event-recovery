function process(event, errors) {
  var fields = tsvToArray(event);
    if (fields[5] == 'GET') {
        var querystring = parseQuerystring(fields[11]);
        querystring['p'] = 'mob';
        fields[11] = buildQuerystring(querystring);
    }
    return arrayToTsv(fields);
}

