settings: {
    'import/extensions': ['.js', '.jsx', '.ts', '.tsx'],
        'import/parsers': {
        '@typescript-eslint/parser': ['.ts', '.tsx'],
    },
    'import/resolver': {
        +   'babel-module': { },
        node: {
            extensions: ['.js', '.jsx', '.ts', '.tsx', '.native.js'],
      }
    }
}