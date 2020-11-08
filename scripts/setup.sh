set -e

if ! git diff-index --quiet HEAD
then
    echo "Error: Found uncommitted changes."
    exit 1
fi

if [ ! -f answers.txt ]
then
    echo "Error: answers.txt file not found."
    exit 1
fi

echo "Applying auto-encrypt/decrypt filters..."
git config filter.euler.clean './scripts/encrypt.sh'
git config filter.euler.smudge './scripts/encrypt.sh -d'
git config diff.euler.textconv 'cat $1 | ./scripts/encrypt.sh -d'

echo "Force rerunning git checkout to decrypt files..."
rm .git/index
git checkout HEAD -- "$(git rev-parse --show-toplevel)"

echo "Done."

